package com.gianmarques.estoqueapi.security.jwt;

import com.gianmarques.estoqueapi.entity.Estoquista;
import com.gianmarques.estoqueapi.entity.Fornecedor;
import com.gianmarques.estoqueapi.entity.Usuario;
import com.gianmarques.estoqueapi.repository.UsuarioRepository;
import com.gianmarques.estoqueapi.service.EstoquistaService;
import com.gianmarques.estoqueapi.service.FornecedorService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final EstoquistaService estoquistaService;
    private String perfil;

    private final FornecedorService fornecedorService;

    public JwtUserDetailsService(FornecedorService fornecedorService, UsuarioRepository usuarioRepository, EstoquistaService estoquistaService) {
        this.fornecedorService = fornecedorService;
        this.usuarioRepository = usuarioRepository;
        this.estoquistaService = estoquistaService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Usuario> optionalUsuario = usuarioRepository.findByEmail(username);

        if (optionalUsuario.isPresent() && optionalUsuario.get().getPerfil().toString().equals("ROLE_ADMIN")) {
            perfil = optionalUsuario.get().getPerfil().toString();
            return new JwtUserDetails(optionalUsuario.get());
        }

        Estoquista estoquista = estoquistaService.buscarEmail(username);
        if (estoquista != null) {
            perfil = estoquista.getPerfil().toString();
            return new JwtUserDetails(estoquista);
        }

        Fornecedor fornecedor = fornecedorService.buscarPorEmail(username);
        if (fornecedor != null) {
            perfil = fornecedor.getPerfil().toString();
            return new JwtUserDetails(fornecedor);
        }
        throw new UsernameNotFoundException("Usuário não encontrado.");

    }
    public JwtToken getAuthenticatedToken(String email) {
        return JwtUtils.criarToken(email, perfil.substring("ROLE_".length()));
    }
}
