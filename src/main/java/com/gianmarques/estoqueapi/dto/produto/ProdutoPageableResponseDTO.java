package com.gianmarques.estoqueapi.dto.produto;

import com.gianmarques.estoqueapi.entity.Fornecedor;

public class ProdutoPageableResponseDTO {

    private Long id;
    private String nome;
    private String categoria;
    private String fornecedor;

    public ProdutoPageableResponseDTO(Long id, String nome, String categoria, Fornecedor fornecedor) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.fornecedor = fornecedor.getNome();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
