package com.gianmarques.estoqueapi.repository.projection;


import com.gianmarques.estoqueapi.entity.Fornecedor;

public interface ProdutoPorFornecedorProjection {

    Long getId();

    String getNome();

    String getCategoria();

    Fornecedor getFornecedor();
}
