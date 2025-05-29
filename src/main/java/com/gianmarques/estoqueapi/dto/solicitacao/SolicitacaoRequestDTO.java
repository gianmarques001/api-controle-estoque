package com.gianmarques.estoqueapi.dto.solicitacao;

import jakarta.validation.constraints.NotNull;

public record SolicitacaoRequestDTO(@NotNull Long idProduto, @NotNull Integer qtdSolicitada) {
}
