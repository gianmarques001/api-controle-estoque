package com.gianmarques.estoqueapi.dto;

public record ProdutoRequestDto(String nome, String descricao, Float preco, String categoria, Integer quantidade) {
}
