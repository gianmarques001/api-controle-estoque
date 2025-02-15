package com.gianmarques.estoqueapi.repository;

import com.gianmarques.estoqueapi.entity.enums.EPerfil;
import com.gianmarques.estoqueapi.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);

    List<Usuario> findAllByPerfil(EPerfil perfil);

    Optional<Usuario> findByPerfil(EPerfil perfil);
}
