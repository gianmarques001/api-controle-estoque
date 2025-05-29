package com.gianmarques.estoqueapi.controller;


import com.gianmarques.estoqueapi.dto.login.LoginDTO;
import com.gianmarques.estoqueapi.exception.exceptions.LoginInvalidoException;
import com.gianmarques.estoqueapi.exception.model.ErrorMessage;
import com.gianmarques.estoqueapi.security.jwt.JwtToken;
import com.gianmarques.estoqueapi.security.jwt.JwtUserDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Autenticação", description = "Controller para se autenticar.")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUserDetailsService userDetailsService;

    public AuthController(AuthenticationManager authenticationManager, JwtUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }


    @Operation(summary = "Autenticar um usuário", description = "Endpoint para autenticar um usuário no sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sucesso na autenticação, retorno de um token.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = JwtToken.class))),
                    @ApiResponse(responseCode = "400", description = "Credenciais inválidas.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Campos inválidos",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
            })
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
