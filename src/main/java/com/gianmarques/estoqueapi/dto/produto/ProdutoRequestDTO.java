package com.gianmarques.estoqueapi.dto.produto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProdutoRequestDTO(@NotBlank @Size(min = 5, max = 15) String nome,
                                @NotNull @Size(min = 10, max = 30) String descricao, @NotNull @Min(1) Float preco,
                                @NotNull @Min(1) Integer quantidade,
                                @NotBlank String categoria) {

}
