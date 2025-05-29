package com.gianmarques.estoqueapi.security.jwt;

import com.gianmarques.estoqueapi.entity.Usuario;
import com.gianmarques.estoqueapi.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    private String perfil;

    public JwtUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Usuario> optUsuario = usuarioRepository.findByEmail(username);

        if (optUsuario.isPresent()) {
            perfil = optUsuario.get().getPerfil().stream().findFirst().get().getNome();
            return new JwtUserDetails(optUsuario.get());
        }

        throw new UsernameNotFoundException("Usuário não encontrado.");
    }

    public JwtToken getAuthenticatedToken(String email) {
        return JwtUtils.gerarToken(email, perfil.substring("ROLE_".length()));
    }
}
