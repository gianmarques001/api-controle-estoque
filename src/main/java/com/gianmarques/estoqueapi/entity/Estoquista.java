package com.gianmarques.estoqueapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "tb_estoquistas")
public class Estoquista extends Usuario {


    @OneToMany(mappedBy = "estoquista")
    private List<Solicitacao> solicitacoes;

    public Estoquista() {
    }

    public Estoquista(Long id, String nome, String email, String senha, List<Solicitacao> solicitacoes) {
        super(id, nome, email, senha);
        this.solicitacoes = solicitacoes;
    }



}
