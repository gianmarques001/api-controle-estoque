package com.gianmarques.estoqueapi.dto.fornecedor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record FornecedorRequestDTO(@NotNull @Size(min = 5, max = 20) String nome, @Email String email,
                                   @NotNull @Size(min = 8, max = 50) String senha,
                                   @NotNull @Size(min = 10, max = 13) String telefone) {
}

