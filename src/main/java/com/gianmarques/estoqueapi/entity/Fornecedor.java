package com.gianmarques.estoqueapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gianmarques.estoqueapi.entity.enums.ERole;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tb_fornecedores")
@EntityListeners(AuditingEntityListener.class)
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 100)
    private String senha;

    @Column(nullable = false, unique = true)
    private String telefone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ERole perfil = ERole.ROLE_FORNECEDOR;

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

    public Fornecedor(Long id, String nome, String email, String senha, String telefone, List<Produto> produtos) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.produtos = produtos;
    }

    public Fornecedor(Long id, String nome, String email, String senha, String telefone, ERole perfil, List<Produto> produtos, LocalDateTime dataCriacao, LocalDateTime dataMoficacao, String criadoPor, String modificadoPor) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.perfil = perfil;
        this.produtos = produtos;
        this.dataCriacao = dataCriacao;
        this.dataMoficacao = dataMoficacao;
        this.criadoPor = criadoPor;
        this.modificadoPor = modificadoPor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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

    public ERole getPerfil() {
        return perfil;
    }

    public void setPerfil(ERole perfil) {
        this.perfil = perfil;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataMoficacao() {
        return dataMoficacao;
    }

    public void setDataMoficacao(LocalDateTime dataMoficacao) {
        this.dataMoficacao = dataMoficacao;
    }

    public String getCriadoPor() {
        return criadoPor;
    }

    public void setCriadoPor(String criadoPor) {
        this.criadoPor = criadoPor;
    }

    public String getModificadoPor() {
        return modificadoPor;
    }

    public void setModificadoPor(String modificadoPor) {
        this.modificadoPor = modificadoPor;
    }

    @Override
    public String toString() {
        return "Fornecedor{" +
                "id=" + id +
                ", nome='" + nome + '\'' +

                '}';
    }


}
