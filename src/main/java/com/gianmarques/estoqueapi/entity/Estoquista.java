package com.gianmarques.estoqueapi.entity;

import com.gianmarques.estoqueapi.entity.enums.ERole;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tb_estoquistas")
public class Estoquista extends Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ERole perfil = ERole.ROLE_ESTOQUISTA;

    @OneToMany(mappedBy = "estoquista")
    private List<Solicitacao> solicitacoes;

    public Estoquista() {
    }



    public Estoquista(Long id, String nome, String email, String senha,  List<Solicitacao> solicitacoes) {
        super(id, nome, email, senha);
        this.solicitacoes = solicitacoes;
        this.id = id;
    }

    public List<Solicitacao> getSolicitacoes() {
        return solicitacoes;
    }

    public void setSolicitacoes(List<Solicitacao> solicitacoes) {
        this.solicitacoes = solicitacoes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
