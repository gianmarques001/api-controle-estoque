package com.gianmarques.estoqueapi.dto.produto;

import com.gianmarques.estoqueapi.entity.Fornecedor;

public class ProdutoPageableResponseDTO {

    private String nome;
    private String categoria;

    private String fornecedor;

    public ProdutoPageableResponseDTO(String nome, String categoria, Fornecedor fornecedor) {
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
}
