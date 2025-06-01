package com.gianmarques.estoqueapi;


import com.gianmarques.estoqueapi.dto.fornecedor.FornecedorDetailsResponseDTO;
import com.gianmarques.estoqueapi.dto.fornecedor.FornecedorRequestDTO;
import com.gianmarques.estoqueapi.dto.fornecedor.FornecedorResponseDTO;
import com.gianmarques.estoqueapi.dto.fornecedor.FornecedorUpdateRequestDTO;
import com.gianmarques.estoqueapi.exception.model.ErrorMessage;
import com.gianmarques.estoqueapi.jwt.JwtAuthentication;
import com.gianmarques.estoqueapi.repository.pageable.GenericPageable;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@Sql(scripts = "/sql/insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/fornecedor.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/produto.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Profile("test")
public class FornecedorIT {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void criarFornecedorValido_Retorno201() {
        FornecedorResponseDTO responseBody = webTestClient
                .post()
                .uri("/api/v1/fornecedores")
                .headers(JwtAuthentication.getHeadersAuthorization(webTestClient, "admin101@test.com", "12345678"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new FornecedorRequestDTO("Fornecedor 100", "fornecedor100@gmail.com",
                        "12345678", "1140028922"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(FornecedorResponseDTO.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody.id()).isNotNull();
        Assertions.assertThat(responseBody.nome()).isEqualTo("Fornecedor 100");
    }

    @Test
    void criarFornecedorInvalido_Retorno422() {
        ErrorMessage responseBody = webTestClient
                .post()
                .uri("/api/v1/fornecedores")
                .headers(JwtAuthentication.getHeadersAuthorization(webTestClient, "admin101@test.com", "12345678"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new FornecedorRequestDTO("Fornecedor 100", "fornecedor100@gmail.com",
                        null, "1140028922"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody.getStatusCode()).isEqualTo(422);
    }

    @Test
    void editarFornecedorValido_Retorno200() {
        FornecedorDetailsResponseDTO responseBody = webTestClient
                .patch()
                .uri("/api/v1/fornecedores/201")
                .headers(JwtAuthentication.getHeadersAuthorization(webTestClient, "admin101@test.com", "12345678"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new FornecedorUpdateRequestDTO("Fornecedor 201", "1140028923"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(FornecedorDetailsResponseDTO.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody.telefone()).isEqualTo("1140028923");
    }

    @Test
    void editarFornecedorInvalido_Retorno404() {
        ErrorMessage responseBody = webTestClient
                .patch()
                .uri("/api/v1/fornecedores/20")
                .headers(JwtAuthentication.getHeadersAuthorization(webTestClient, "admin101@test.com", "12345678"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new FornecedorUpdateRequestDTO("Fornecedor 201", "1140028923"))
                .exchange()
                .expectStatus().isEqualTo(404)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody.getStatusCode()).isEqualTo(404);
        Assertions.assertThat(responseBody.getMessage()).isEqualTo("Fornecedor não encontrado.");
    }

    @Test
    void buscarFornecedorValido_Retorno200() {

        FornecedorDetailsResponseDTO responseBody = webTestClient
                .get()
                .uri("/api/v1/fornecedores/201")
                .headers(JwtAuthentication.getHeadersAuthorization(webTestClient, "admin101@test.com", "12345678"))
                .exchange()
                .expectStatus().isEqualTo(200)
                .expectBody(FornecedorDetailsResponseDTO.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody.nome()).isEqualTo("Fornecedor 201");
        Assertions.assertThat(responseBody.email()).isEqualTo("fornecedor201@test.com");
    }

    @Test
    void buscarFornecedorInvalido_Retorno404() {

        ErrorMessage responseBody = webTestClient
                .get()
                .uri("/api/v1/fornecedores/202")
                .headers(JwtAuthentication.getHeadersAuthorization(webTestClient, "admin101@test.com", "12345678"))
                .exchange()
                .expectStatus().isEqualTo(404)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody.getStatusCode()).isEqualTo(404);
        Assertions.assertThat(responseBody.getMessage()).isEqualTo("Fornecedor não encontrado.");
    }

    @Test
    void deletarFornecedorValido_Retorno204() {

        webTestClient
                .delete()
                .uri("/api/v1/fornecedores/201")
                .headers(JwtAuthentication.getHeadersAuthorization(webTestClient, "admin101@test.com", "12345678"))
                .exchange()
                .expectStatus().isEqualTo(204);
    }


    @Test
    void deletarFornecedorInvalido_Retorno404() {

        ErrorMessage responseBody = webTestClient
                .delete()
                .uri("/api/v1/fornecedores/202")
                .headers(JwtAuthentication.getHeadersAuthorization(webTestClient, "admin101@test.com", "12345678"))
                .exchange()
                .expectStatus().isEqualTo(404)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody.getStatusCode()).isEqualTo(404);
        Assertions.assertThat(responseBody.getMessage()).isEqualTo("Fornecedor não encontrado.");
    }


    @Test
    void salvarFornecedorDuplicado_Retorno409() {
        ErrorMessage responseBody = webTestClient
                .post()
                .uri("/api/v1/fornecedores")
                .headers(JwtAuthentication.getHeadersAuthorization(webTestClient, "admin101@test.com", "12345678"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new FornecedorRequestDTO("Fornecedor 201", "fornecedor201@test.com",
                        "12345678", "1140028922"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody.getStatusCode()).isEqualTo(409);
        Assertions.assertThat(responseBody.getMessage()).isEqualTo("Dados já existentes (E-mail ou Telefone), informe outro.");
    }

    @Test
    void buscarProdutosPorFornecedor_Retorno200() {

        GenericPageable responseBody = webTestClient
                .get()
                .uri("/api/v1/produtos/fornecedor")
                .headers(JwtAuthentication.getHeadersAuthorization(webTestClient, "fornecedor201@test.com", "12345678"))
                .exchange()
                .expectStatus().isEqualTo(200)
                .expectBody(GenericPageable.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getTotalElements()).isEqualTo(1);
    }

}
