package com.gianmarques.estoqueapi.dto.solicitacao;

public class SolicitacaoFornecedorPageableResponseDTO {

    private Long idSolicitacao;

    private String nomeProduto;

    private Integer quantidadeSolicitada;

    private String nomeEstoquista;


    public SolicitacaoFornecedorPageableResponseDTO() {
    }

    public SolicitacaoFornecedorPageableResponseDTO(Long idSolicitacao, String nomeProduto, Integer quantidadeSolicitada, String nomeEstoquista) {
        this.idSolicitacao = idSolicitacao;
        this.nomeProduto = nomeProduto;
        this.quantidadeSolicitada = quantidadeSolicitada;
        this.nomeEstoquista = nomeEstoquista;
    }

    public Long getIdSolicitacao() {
        return idSolicitacao;
    }

    public void setIdSolicitacao(Long idSolicitacao) {
        this.idSolicitacao = idSolicitacao;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public Integer getQuantidadeSolicitada() {
        return quantidadeSolicitada;
    }

    public void setQuantidadeSolicitada(Integer quantidadeSolicitada) {
        this.quantidadeSolicitada = quantidadeSolicitada;
    }

    public String getNomeEstoquista() {
        return nomeEstoquista;
    }

    public void setNomeEstoquista(String nomeEstoquista) {
        this.nomeEstoquista = nomeEstoquista;
    }
}
