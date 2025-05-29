package com.gianmarques.estoqueapi.component;

import com.gianmarques.estoqueapi.entity.Permissao;
import com.gianmarques.estoqueapi.entity.Usuario;
import com.gianmarques.estoqueapi.repository.PermissaoRepository;
import com.gianmarques.estoqueapi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PermissaoRepository permissaoRepository;

    @Override
    public void run(String... args) throws Exception {

        if (permissaoRepository.count() == 0) {
            List<Permissao> permissoes = Arrays.asList(
                    new Permissao("ROLE_ADMIN"),
                    new Permissao("ROLE_ESTOQUISTA"),
                    new Permissao("ROLE_FORNECEDOR"));
            permissaoRepository.saveAll(permissoes);

        }

        String email = "admin01@gmail.com";
        Optional<Usuario> optionalEmail = usuarioRepository.findByEmail(email);

        if (!optionalEmail.isPresent()) {
            Permissao nomePermissao = permissaoRepository.findByNome("ROLE_ADMIN");
            Usuario usuarioAdmin = new Usuario();
            usuarioAdmin.setNome("adminMaster");
            usuarioAdmin.setEmail(email);
            usuarioAdmin.setPerfil(Set.of(nomePermissao));
            usuarioAdmin.setSenha(passwordEncoder.encode("123456789"));
            usuarioRepository.save(usuarioAdmin);
            System.out.println("Usuário salvo");
        }
    }
}
