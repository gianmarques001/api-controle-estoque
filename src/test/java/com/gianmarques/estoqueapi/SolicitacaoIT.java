package com.gianmarques.estoqueapi;


import com.gianmarques.estoqueapi.dto.produto.ProdutoUpdateRequestDTO;
import com.gianmarques.estoqueapi.dto.solicitacao.SolicitacaoRequestDTO;
import com.gianmarques.estoqueapi.exception.model.ErrorMessage;
import com.gianmarques.estoqueapi.jwt.JwtAuthentication;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"/sql/insert.sql", "/sql/fornecedor.sql", "/sql/estoquista.sql", "/sql/produto.sql", "/sql/solicitacao.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Profile("test")
public class SolicitacaoIT {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void criarSolicitacaoValida_Retorno201() {
        webTestClient.
                post()
                .uri("/api/v1/solicitacoes")
                .headers(JwtAuthentication.getHeadersAuthorization(webTestClient, "estoquista202@test.com", "12345678"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new SolicitacaoRequestDTO(301L, 10))
                .exchange()
                .expectStatus().isCreated();

    }

    @Test
    void criarSolicitacaoInvalida_Retorno422() {
        webTestClient.
                post()
                .uri("/api/v1/solicitacoes")
                .headers(JwtAuthentication.getHeadersAuthorization(webTestClient, "estoquista202@test.com", "12345678"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new SolicitacaoRequestDTO(301L, null))
                .exchange()
                .expectStatus().isEqualTo(422);
    }

    @Test
    void editarSolicitacaoValidaPeloFornecedor_Retorno204() {

        webTestClient.
                patch()
                .uri("/api/v1/solicitacoes/fornecedores/401")
                .headers(JwtAuthentication.getHeadersAuthorization(webTestClient, "fornecedor201@test.com", "12345678"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ProdutoUpdateRequestDTO(10, 20F))
                .exchange()
                .expectStatus().isEqualTo(204);
    }

    @Test
    void editarSolicitacaoInvalidaPeloFornecedor_Retorno200() {

        ErrorMessage responseBody = webTestClient.
                patch()
                .uri("/api/v1/solicitacoes/fornecedores/1")
                .headers(JwtAuthentication.getHeadersAuthorization(webTestClient, "fornecedor201@test.com", "12345678"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ProdutoUpdateRequestDTO(10, 20F))
                .exchange()
                .expectStatus().isEqualTo(404)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody.getStatusCode()).isEqualTo(404);
        Assertions.assertThat(responseBody.getMessage()).isEqualTo("Solicitacao não encontrada");

        ErrorMessage responseBody2 = webTestClient.
                patch()
                .uri("/api/v1/solicitacoes/fornecedores/401")
                .headers(JwtAuthentication.getHeadersAuthorization(webTestClient, "fornecedor201@test.com", "12345678"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ProdutoUpdateRequestDTO(null, 20F))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody2.getStatusCode()).isEqualTo(422);
        Assertions.assertThat(responseBody2.getMessage()).isEqualTo("Alguns campos estão invalidos");


    }

    @Test
    void concluirSolicitacao_Retorno200() {

        webTestClient.
                patch()
                .uri("/api/v1/solicitacoes/baixa")
                .headers(JwtAuthentication.getHeadersAuthorization(webTestClient, "estoquista202@test.com", "12345678"))
                .exchange()
                .expectStatus().isOk();
    }

}

