package com.gianmarques.estoqueapi.component;

import com.gianmarques.estoqueapi.entity.Usuario;
import com.gianmarques.estoqueapi.entity.enums.ERole;
import com.gianmarques.estoqueapi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        String email = "admin000@gmail.com";
        Optional<Usuario> optionalEmail = usuarioRepository.findByEmail(email);

        if (!optionalEmail.isPresent()) {
            Usuario usuarioAdmin = new Usuario();
            usuarioAdmin.setNome("adminMaster");
            usuarioAdmin.setEmail("admin000@gmail.com");
            usuarioAdmin.setPerfil(ERole.ROLE_ADMIN);
            usuarioAdmin.setSenha(passwordEncoder.encode("12345678"));
            usuarioRepository.save(usuarioAdmin);
        }


    }
}
