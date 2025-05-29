package com.gianmarques.estoqueapi.dto.produto;

public record ProdutoDetailsResponseDTO(Long id, String nome, String descricao, Float preco, Integer quantidade,
                                        String categoria) {
}
