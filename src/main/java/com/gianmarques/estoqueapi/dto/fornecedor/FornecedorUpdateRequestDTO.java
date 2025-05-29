package com.gianmarques.estoqueapi.dto.fornecedor;

import jakarta.validation.constraints.NotBlank;

public record FornecedorUpdateRequestDTO(@NotBlank String nome, @NotBlank String telefone) {
}
