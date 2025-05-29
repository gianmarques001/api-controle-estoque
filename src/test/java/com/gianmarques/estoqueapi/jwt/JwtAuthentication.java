package com.gianmarques.estoqueapi.jwt;


import com.gianmarques.estoqueapi.dto.login.LoginDTO;
import com.gianmarques.estoqueapi.security.jwt.JwtToken;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.function.Consumer;

public class JwtAuthentication {

    public static Consumer<HttpHeaders> getHeadersAuthorization(WebTestClient client, String email, String password) {
        String token = client
                .post()
                .uri("/api/v1/auth")
                .bodyValue(new LoginDTO(email, password))
                .exchange()
                .expectBody(JwtToken.class)
                .returnResult().getResponseBody().getToken();

        return httpHeaders -> httpHeaders.add("Authorization", "Bearer " + token);
    }

}

