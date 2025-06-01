package com.gianmarques.estoqueapi;

import com.gianmarques.estoqueapi.dto.login.LoginDTO;
import com.gianmarques.estoqueapi.exception.model.ErrorMessage;
import com.gianmarques.estoqueapi.security.jwt.JwtToken;
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
@Sql(scripts = "/sql/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Profile("test")
public class JwtIT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void autenticarComCredenciasValidas_Retorno200() {

        JwtToken responseBody = webTestClient.post()
                .uri("api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new LoginDTO("admin101@test.com", "12345678"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(JwtToken.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();

    }

    @Test
    public void autenticarComCredenciasInvalidas_Retorno401() {

        ErrorMessage responseBody = webTestClient.post()
                .uri("api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new LoginDTO("admin101@test.com", "123456"))
                .exchange()
                .expectStatus().isEqualTo(401)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody.getMessage()).isEqualTo("Erro ao se autenticar.");

    }
}
