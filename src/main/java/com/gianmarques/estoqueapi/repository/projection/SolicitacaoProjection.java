package com.gianmarques.estoqueapi.repository.projection;

public interface SolicitacaoProjection {

    Long getId();

    String getNomeProduto();

    String getStatus();

    Integer getQuantidadeSolicitada();

    Integer getQuantidadeAtendida();


}
