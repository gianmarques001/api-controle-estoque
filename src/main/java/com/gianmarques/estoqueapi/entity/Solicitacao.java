package com.gianmarques.estoqueapi.entity;

import com.gianmarques.estoqueapi.entity.enums.EStatusSolicitacao;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_solicitacoes")
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
    private EStatusSolicitacao status = EStatusSolicitacao.SOLICITADO;

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

    public Solicitacao(Long id, Estoquista estoquista, Produto produto, EStatusSolicitacao status, Integer quantidadeSolicitada, Integer quantidadeAtendida) {
        this.id = id;
        this.estoquista = estoquista;
        this.produto = produto;
        this.status = status;
        this.quantidadeSolicitada = quantidadeSolicitada;
        this.quantidadeAtendida = quantidadeAtendida;
    }

    public Solicitacao(Long id, Estoquista estoquista, Produto produto, EStatusSolicitacao status, Integer quantidadeSolicitada, Integer quantidadeAtendida, LocalDateTime dataCriacao, LocalDateTime dataMoficacao, String criadoPor, String modificadoPor) {
        this.id = id;
        this.estoquista = estoquista;
        this.produto = produto;
        this.status = status;
        this.quantidadeSolicitada = quantidadeSolicitada;
        this.quantidadeAtendida = quantidadeAtendida;
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

    public EStatusSolicitacao getStatus() {
        return status;
    }

    public void setStatus(EStatusSolicitacao status) {
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
        return "Solicitacao{" +
                "id=" + id +
                ", estoquista=" + estoquista.getId() +
                ", produto=" + produto.getNome() +
                ", status=" + status +
                ", quantidadeSolicitada=" + quantidadeSolicitada +
                ", quantidadeAtendida=" + quantidadeAtendida +
                '}';
    }
}
