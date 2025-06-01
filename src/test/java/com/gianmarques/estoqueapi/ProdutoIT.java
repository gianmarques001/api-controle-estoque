package com.gianmarques.estoqueapi;

import com.gianmarques.estoqueapi.dto.produto.*;
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
@Sql(scripts = {"/sql/fornecedor.sql", "/sql/produto.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Profile("test")
public class ProdutoIT {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void criarProdutoValido_Retorno201() {

        ProdutoResponseDTO responseBody = webTestClient
                .post()
                .uri("/api/v1/produtos")
                .headers(JwtAuthentication.getHeadersAuthorization(webTestClient, "fornecedor201@test.com", "12345678"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ProdutoRequestDTO("Produto 1", "Descrição Produto 1",
                        12F, 10, "LIMPEZA"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ProdutoResponseDTO.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody.id()).isNotNull();
        Assertions.assertThat(responseBody.nome()).isEqualTo("Produto 1");
    }

    @Test
    void criarProdutoInvalido_Retorno422() {

        ErrorMessage responseBody = webTestClient
                .post()
                .uri("/api/v1/produtos")
                .headers(JwtAuthentication.getHeadersAuthorization(webTestClient, "fornecedor201@test.com", "12345678"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ProdutoRequestDTO("Produto 1", "Descrição Produto 1",
                        12F, 10, null))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody.getStatusCode()).isEqualTo(422);
        Assertions.assertThat(responseBody.getMessage()).isEqualTo("Alguns campos estão invalidos");
    }

    @Test
    void editarProdutoValido_Retorno200() {

        ProdutoResponseDTO responseBody = webTestClient
                .post()
                .uri("/api/v1/produtos")
                .headers(JwtAuthentication.getHeadersAuthorization(webTestClient, "fornecedor201@test.com", "12345678"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ProdutoRequestDTO("Produto 1", "Descrição Produto 1",
                        12F, 10, "LIMPEZA"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ProdutoResponseDTO.class)
                .returnResult().getResponseBody();


        Long idProduto = responseBody.id();

        ProdutoUpdateResponseDTO responseBody2 = webTestClient
                .patch()
                .uri("/api/v1/produtos/" + idProduto)
                .headers(JwtAuthentication.getHeadersAuthorization(webTestClient, "fornecedor201@test.com", "12345678"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ProdutoUpdateRequestDTO(30, 12F))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProdutoUpdateResponseDTO.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody2.quantidade()).isEqualTo(30);
    }

    @Test
    void editarProdutoInvalido_Retorno404() {

        ProdutoResponseDTO responseBody = webTestClient
                .post()
                .uri("/api/v1/produtos")
                .headers(JwtAuthentication.getHeadersAuthorization(webTestClient, "fornecedor201@test.com", "12345678"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ProdutoRequestDTO("Produto 1", "Descrição Produto 1",
                        12F, 10, "LIMPEZA"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ProdutoResponseDTO.class)
                .returnResult().getResponseBody();


        Long idProduto = responseBody.id();

        ErrorMessage responseBody2 = webTestClient
                .patch()
                .uri("/api/v1/produtos/" + idProduto + 1)
                .headers(JwtAuthentication.getHeadersAuthorization(webTestClient, "fornecedor201@test.com", "12345678"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ProdutoUpdateRequestDTO(30, 12F))
                .exchange()
                .expectStatus().isEqualTo(404)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody2.getStatusCode()).isEqualTo(404);
        Assertions.assertThat(responseBody2.getMessage()).isEqualTo("Produto não encontrado");
    }

    @Test
    void editarProdutoInvalido_Retorno422() {

        ProdutoResponseDTO responseBody = webTestClient
                .post()
                .uri("/api/v1/produtos")
                .headers(JwtAuthentication.getHeadersAuthorization(webTestClient, "fornecedor201@test.com", "12345678"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ProdutoRequestDTO("Produto 1", "Descrição Produto 1",
                        12F, 10, "LIMPEZA"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ProdutoResponseDTO.class)
                .returnResult().getResponseBody();


        Long idProduto = responseBody.id();

        ErrorMessage responseBody2 = webTestClient
                .patch()
                .uri("/api/v1/produtos/" + idProduto)
                .headers(JwtAuthentication.getHeadersAuthorization(webTestClient, "fornecedor201@test.com", "12345678"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ProdutoUpdateRequestDTO(null, 12F))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody2.getStatusCode()).isEqualTo(422);
        Assertions.assertThat(responseBody2.getMessage()).isEqualTo("Alguns campos estão invalidos");
    }

    @Test
    void buscarProdutoValido_Retorno200() {

        ProdutoResponseDTO responseBody = webTestClient
                .post()
                .uri("/api/v1/produtos")
                .headers(JwtAuthentication.getHeadersAuthorization(webTestClient, "fornecedor201@test.com", "12345678"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ProdutoRequestDTO("Produto 1", "Descrição Produto 1",
                        12F, 10, "LIMPEZA"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ProdutoResponseDTO.class)
                .returnResult().getResponseBody();


        Long idProduto = responseBody.id();


        ProdutoDetailsResponseDTO responseBody2 = webTestClient
                .get()
                .uri("/api/v1/produtos/" + idProduto)
                .headers(JwtAuthentication.getHeadersAuthorization(webTestClient, "fornecedor201@test.com", "12345678"))
                .exchange()
                .expectStatus().isEqualTo(200)
                .expectBody(ProdutoDetailsResponseDTO.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody2.nome()).isEqualTo("Produto 1");
    }

    @Test
    void buscarProdutoInvalido_Retorno404() {

        ProdutoResponseDTO responseBody = webTestClient
                .post()
                .uri("/api/v1/produtos")
                .headers(JwtAuthentication.getHeadersAuthorization(webTestClient, "fornecedor201@test.com", "12345678"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ProdutoRequestDTO("Produto 1", "Descrição Produto 1",
                        12F, 10, "LIMPEZA"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ProdutoResponseDTO.class)
                .returnResult().getResponseBody();

        Long idProduto = responseBody.id();

        ErrorMessage responseBody2 = webTestClient
                .get()
                .uri("/api/v1/produtos/" + idProduto + 1)
                .headers(JwtAuthentication.getHeadersAuthorization(webTestClient, "fornecedor201@test.com", "12345678"))
                .exchange()
                .expectStatus().isEqualTo(404)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody2.getMessage()).isEqualTo("Produto não encontrado");
    }

    @Test
    void deletarProdutoValido_Retorno204() {

        ProdutoResponseDTO responseBody = webTestClient
                .post()
                .uri("/api/v1/produtos")
                .headers(JwtAuthentication.getHeadersAuthorization(webTestClient, "fornecedor201@test.com", "12345678"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ProdutoRequestDTO("Produto 1", "Descrição Produto 1",
                        12F, 10, "LIMPEZA"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ProdutoResponseDTO.class)
                .returnResult().getResponseBody();

        Long idProduto = responseBody.id();

        webTestClient
                .delete()
                .uri("/api/v1/produtos/" + idProduto)
                .headers(JwtAuthentication.getHeadersAuthorization(webTestClient, "fornecedor201@test.com", "12345678"))
                .exchange()
                .expectStatus().isEqualTo(204);
    }

    @Test
    void deletarProdutoInvalido_Retorno404() {
        webTestClient
                .delete()
                .uri("/api/v1/produtos/200")
                .headers(JwtAuthentication.getHeadersAuthorization(webTestClient, "fornecedor201@test.com", "12345678"))
                .exchange()
                .expectStatus().isEqualTo(404);
    }


    @Test
    void salvarProdutoDuplicado_Retorno409() {

        ErrorMessage responseBody = webTestClient
                .post()
                .uri("/api/v1/produtos")
                .headers(JwtAuthentication.getHeadersAuthorization(webTestClient, "fornecedor201@test.com", "12345678"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ProdutoRequestDTO("PRODUTO 2", "DESCRIÇÃO PRODUTO 2",
                        12F, 10, "LIMPEZA"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody.getStatusCode()).isEqualTo(409);
        Assertions.assertThat(responseBody.getMessage()).isEqualTo("Produto Duplicado");
    }


    @Test
    void listarProdutosFornecedores_Retorno200() {

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

