package com.gianmarques.estoqueapi;


import com.gianmarques.estoqueapi.entity.enums.EPerfil;
import com.gianmarques.estoqueapi.entity.Usuario;
import com.gianmarques.estoqueapi.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class UsuarioTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void salvarUsuarioAdmin() {
        Usuario usuario = new Usuario();
        usuario.setNome("Gian Marques");
        usuario.setEmail("gianmarques01@gmail.com");
        usuario.setSenha("12345678");
        usuario.setPerfil(EPerfil.ADMIN);
        usuarioRepository.save(usuario);
    }

    @Test
    public void salvarUsuarioEstoquista() {
        Usuario usuario = new Usuario();
        usuario.setNome("Estoquista 1");
        usuario.setEmail("estoquista01@gmail.com");
        usuario.setSenha("12345678");
        usuario.setPerfil(EPerfil.ESTOQUISTA);
        usuarioRepository.save(usuario);
    }


    @Test
    public void buscarUsuarioPorEmail() {
        String email = "gianmarques@gmail.com";
        Optional<Usuario> optionalUsuario = usuarioRepository.findByEmail(email);
        optionalUsuario.ifPresent(usuario -> System.out.println(usuario.getNome()));
    }

    @Test
    public void editarUsuarioPorEmail() {
        String email = "gianmarques@gmail.com";
        Optional<Usuario> optionalUsuario = usuarioRepository.findByEmail(email);
        Usuario usuario = optionalUsuario.orElse(null);
        usuario.setSenha("123456789");
        usuarioRepository.save(usuario);
    }

    @Test
    public void buscarUsuarioPorId() {
        Long id = 1L;
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        optionalUsuario.ifPresent(usuario -> System.out.println(usuario.getNome()));
    }

    @Test
    public void listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        usuarios.forEach(System.out::println);
    }


}
