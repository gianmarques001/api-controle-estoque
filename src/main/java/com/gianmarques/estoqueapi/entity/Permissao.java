package com.gianmarques.estoqueapi.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "tb_permissoes")
public class Permissao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    public Permissao() {
    }

    @ManyToMany(mappedBy = "perfil")
    private Set<Usuario> usuarios;


    public Permissao(String nome) {
        this.nome = nome;
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

    public Set<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
