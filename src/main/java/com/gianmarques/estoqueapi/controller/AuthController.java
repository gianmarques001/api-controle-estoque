package com.gianmarques.estoqueapi.controller;


import com.gianmarques.estoqueapi.dto.login.LoginDTO;
import com.gianmarques.estoqueapi.exception.exceptions.LoginInvalidoException;
import com.gianmarques.estoqueapi.security.jwt.JwtToken;
import com.gianmarques.estoqueapi.security.jwt.JwtUserDetailsService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUserDetailsService userDetailsService;

    public AuthController(AuthenticationManager authenticationManager, JwtUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    // TODO - Estudar o que esse método faz
    @PostMapping
    public ResponseEntity<?> auth(@Valid @RequestBody LoginDTO loginDTO) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.senha());
            authenticationManager.authenticate(authenticationToken);
            JwtToken token = userDetailsService.getAuthenticatedToken(loginDTO.email());
            return ResponseEntity.ok(token);
        } catch (AuthenticationException e) {
            throw new LoginInvalidoException("Erro ao se autenticar.");
        }
    }
}
