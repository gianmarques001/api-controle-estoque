package com.gianmarques.estoqueapi.dto.estoquista;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EstoquistaRequestDTO(@NotBlank @Size(min = 5, max = 20) String nome, @NotBlank @Email String email,
                                   @NotBlank @Size(min = 8, max = 50) String senha) {
}
