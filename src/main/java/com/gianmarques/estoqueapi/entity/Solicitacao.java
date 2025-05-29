package com.gianmarques.estoqueapi.entity;

import com.gianmarques.estoqueapi.entity.enums.StatusSolicitacao;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_solicitacoes")
@EntityListeners(AuditingEntityListener.class)
public class Solicitacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_estoquista", nullable = false)
    private Estoquista estoquista;

    @ManyToOne
    @JoinColumn(name = "id_produto", nullable = false)
    private Produto produto;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusSolicitacao status = StatusSolicitacao.SOLICITADO;

    @Column(nullable = false)
    private Integer quantidadeSolicitada;

    private Integer quantidadeAtendida;


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


    public Solicitacao() {
    }

    public Solicitacao(Long id, Estoquista estoquista, Produto produto, StatusSolicitacao status, Integer quantidadeSolicitada, Integer quantidadeAtendida) {
        this.id = id;
        this.estoquista = estoquista;
        this.produto = produto;
        this.status = status;
        this.quantidadeSolicitada = quantidadeSolicitada;
        this.quantidadeAtendida = quantidadeAtendida;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Estoquista getEstoquista() {
        return estoquista;
    }

    public void setEstoquista(Estoquista estoquista) {
        this.estoquista = estoquista;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public StatusSolicitacao getStatus() {
        return status;
    }

    public void setStatus(StatusSolicitacao status) {
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


}
