package com.gianmarques.estoqueapi.security.jwt;

import com.gianmarques.estoqueapi.entity.Estoquista;
import com.gianmarques.estoqueapi.entity.Fornecedor;
import com.gianmarques.estoqueapi.entity.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public class JwtUserDetails implements UserDetails {

    private final Long id;

    private final String email;
    private final String senha;
    private final String role;


    public JwtUserDetails(Fornecedor fornecedor) {
        this.id = fornecedor.getId();
        this.email = fornecedor.getEmail();
        this.senha = fornecedor.getSenha();
        this.role = fornecedor.getPerfil().toString();
    }

    public JwtUserDetails(Usuario usuario) {
        this.id = usuario.getId();
        this.email = usuario.getEmail();
        this.senha = usuario.getSenha();
        this.role = usuario.getPerfil().toString();
    }

    public JwtUserDetails(Estoquista estoquista) {
        this.id = estoquista.getId();
        this.email = estoquista.getEmail();
        this.senha = estoquista.getSenha();
        this.role = estoquista.getPerfil().toString();
    }


    @Override
    public List<GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(this.role);
    }


    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    public Long getId() {
        return this.id;
    }
}
