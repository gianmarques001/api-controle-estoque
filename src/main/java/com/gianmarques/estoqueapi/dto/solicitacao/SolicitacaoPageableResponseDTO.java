package com.gianmarques.estoqueapi.dto.solicitacao;

import com.fasterxml.jackson.annotation.JsonInclude;

public class SolicitacaoPageableResponseDTO {

    private Long id;

    private String nomeProduto;

    private String status;

    private Integer quantidadeSolicitada;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer quantidadeAtendida;


    public SolicitacaoPageableResponseDTO() {
    }

    public SolicitacaoPageableResponseDTO(Long id, String status, Integer quantidadeSolicitada, Integer quantidadeAtendida, String nomeProduto) {
        this.id = id;
        this.status = status;
        this.quantidadeSolicitada = quantidadeSolicitada;
        this.quantidadeAtendida = quantidadeAtendida;
        this.nomeProduto = nomeProduto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getQuantidadeSolicitada() {
        return quantidadeSolicitada;
    }

    public void setQuantidadeSolicitada(Integer quantidadeSolicitada) {
        this.quantidadeSolicitada = quantidadeSolicitada;
    }

    public Integer getQuantidadeAtendida() {
        return quantidadeAtendida;
    }

    public void setQuantidadeAtendida(Integer quantidadeAtendida) {
        this.quantidadeAtendida = quantidadeAtendida;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}


