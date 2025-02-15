package com.gianmarques.estoqueapi.entity;

import com.gianmarques.estoqueapi.entity.enums.EPerfil;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tb_estoquistas")
public class Estoquista extends Usuario {

    @Enumerated(EnumType.STRING)
    private EPerfil perfil = EPerfil.ESTOQUISTA;

    @OneToMany(mappedBy = "estoquista")
    private List<Solicitacao> solicitacoes;

    public Estoquista() {
    }

    public Estoquista(EPerfil perfil, List<Solicitacao> solicitacoes) {
        this.perfil = perfil;
        this.solicitacoes = solicitacoes;
    }

    public Estoquista(Long id, String nome, String email, String senha, List<Solicitacao> solicitacoes) {
        super(id, nome, email, senha, EPerfil.ESTOQUISTA);
        this.solicitacoes = solicitacoes;
    }

    public List<Solicitacao> getSolicitacoes() {
        return solicitacoes;
    }

    public void setSolicitacoes(List<Solicitacao> solicitacoes) {
        this.solicitacoes = solicitacoes;
    }


}
