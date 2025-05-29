package com.gianmarques.estoqueapi.dto.fornecedor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FornecedorRequestDTO(@NotBlank @Size(min = 5, max = 20) String nome, @NotBlank @Email String email,
                                   @NotBlank @Size(min = 8, max = 50) String senha,
                                   @NotBlank @Size(min = 10, max = 13) String telefone) {
}

