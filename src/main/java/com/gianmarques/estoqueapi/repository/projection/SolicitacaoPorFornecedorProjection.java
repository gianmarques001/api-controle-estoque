package com.gianmarques.estoqueapi.repository.projection;

public interface SolicitacaoPorFornecedorProjection {


    Long getIdSolicitacao();

    String getNomeProduto();

    Integer getQuantidadeSolicitada();

    String getNomeEstoquista();

}

