package com.gianmarques.estoqueapi;

import com.gianmarques.estoqueapi.dto.estoquista.EstoquistaRequestDTO;
import com.gianmarques.estoqueapi.dto.estoquista.EstoquistaResponseDTO;
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
@Sql(scripts = "/sql/insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/estoquista.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Profile("test")
public class EstoquistaIT {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void criarEstoquistaValido_Retorno201() {
        EstoquistaResponseDTO responseBody = webTestClient
                .post()
                .uri("/api/v1/estoquistas")
                .headers(JwtAuthentication.getHeadersAuthorization(webTestClient, "admin101@test.com", "12345678"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new EstoquistaRequestDTO("Estoquista 100", "estoquista100@gmail.com",
                        "12345678"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(EstoquistaResponseDTO.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody.id()).isNotNull();
        Assertions.assertThat(responseBody.nome()).isEqualTo("Estoquista 100");
    }

    @Test
    void criarEstoquistaInvalido_Retorno201() {
        ErrorMessage responseBody = webTestClient
                .post()
                .uri("/api/v1/estoquistas")
                .headers(JwtAuthentication.getHeadersAuthorization(webTestClient, "admin101@test.com", "12345678"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new EstoquistaRequestDTO("Estoquista 100", "estoquista100@gmail.com",
                        null))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody.getStatusCode()).isEqualTo(422);
        Assertions.assertThat(responseBody.getMessage()).isEqualTo("Alguns campos estão invalidos");
    }

    @Test
    void buscarEstoquistaValido_Retorno200() {
        EstoquistaResponseDTO responseBody = webTestClient
                .get()
                .uri("/api/v1/estoquistas/202")
                .headers(JwtAuthentication.getHeadersAuthorization(webTestClient, "admin101@test.com", "12345678"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(EstoquistaResponseDTO.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody.nome()).isEqualTo("Estoquista 202");
    }

    @Test
    void buscarEstoquistaInvalido_Retorno404() {
        ErrorMessage responseBody = webTestClient
                .get()
                .uri("/api/v1/estoquistas/203")
                .headers(JwtAuthentication.getHeadersAuthorization(webTestClient, "admin101@test.com", "12345678"))
                .exchange()
                .expectStatus().isEqualTo(404)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody.getStatusCode()).isEqualTo(404);
        Assertions.assertThat(responseBody.getMessage()).isEqualTo("Estoquista não encontrado.");
    }

    @Test
    void deletarEstoquistaValido_Retorno204() {

        webTestClient
                .delete()
                .uri("/api/v1/estoquistas/202")
                .headers(JwtAuthentication.getHeadersAuthorization(webTestClient, "admin101@test.com", "12345678"))
                .exchange()
                .expectStatus().isEqualTo(204);
    }

    @Test
    void deletarEstoquistaInvalido_Retorno404() {

        ErrorMessage responseBody = webTestClient
                .delete()
                .uri("/api/v1/estoquistas/203")
                .headers(JwtAuthentication.getHeadersAuthorization(webTestClient, "admin101@test.com", "12345678"))
                .exchange()
                .expectStatus().isEqualTo(404)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody.getStatusCode()).isEqualTo(404);
        Assertions.assertThat(responseBody.getMessage()).isEqualTo("Estoquista não encontrado.");
    }

    @Test
    void salvarEstoquistaDuplicado_Retorno409() {
        ErrorMessage responseBody = webTestClient
                .post()
                .uri("/api/v1/estoquistas")
                .headers(JwtAuthentication.getHeadersAuthorization(webTestClient, "admin101@test.com", "12345678"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new EstoquistaRequestDTO("Estoquista 202", "estoquista202@test.com",
                        "12345678"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody.getStatusCode()).isEqualTo(409);
        Assertions.assertThat(responseBody.getMessage()).isEqualTo("Email já cadastrado, informe outro.");
    }

}
