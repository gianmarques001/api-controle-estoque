package com.gianmarques.estoqueapi.dto.produto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ProdutoUpdateRequestDTO(@NotNull @Min(1) Integer quantidade, @NotNull @Min(1) Float preco) {
}
