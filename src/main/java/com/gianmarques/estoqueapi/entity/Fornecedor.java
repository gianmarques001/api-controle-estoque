package com.gianmarques.estoqueapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tb_fornecedores")
@EntityListeners(AuditingEntityListener.class)
public class Fornecedor extends Usuario {

    @Column(nullable = false, unique = true)
    private String telefone;

    @JsonIgnore
    @OneToMany(mappedBy = "fornecedor")
    private List<Produto> produtos;

    @CreatedDate
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @LastModifiedDate
    @Column(name = "data_modificacao")
    private LocalDateTime dataMoficacao;

    @CreatedBy
    @Column(name = "criado_por")
    private String criadoPor;

    @LastModifiedBy
    @Column(name = "modificado_por")
    private String modificadoPor;


    public Fornecedor() {
    }

    public Fornecedor(Long id, String nome, String email, String senha, Set<Permissao> perfil, String telefone) {
        super(id, nome, email, senha, perfil);
        this.telefone = telefone;

    }

    public Fornecedor(Long id, String nome, String email, String senha, Set<Permissao> perfil, String telefone, List<Produto> produtos) {
        super(id, nome, email, senha, perfil);
        this.telefone = telefone;
        this.produtos = produtos;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }




}
