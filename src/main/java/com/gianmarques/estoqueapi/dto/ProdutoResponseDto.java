package com.gianmarques.estoqueapi.dto;

import java.net.URI;

public record ProdutoResponseDto(Long id, String nome, URI url) {
}
