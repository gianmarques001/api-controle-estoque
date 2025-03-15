package com.gianmarques.estoqueapi;


import com.gianmarques.estoqueapi.entity.Estoquista;
import com.gianmarques.estoqueapi.entity.enums.ERole;
import com.gianmarques.estoqueapi.repository.EstoquistaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class EstoquistaTest {

    @Autowired
    private EstoquistaRepository estoquistaRepository;


    @Test
    public void salvarEstoquista() {
        Estoquista estoquista = new Estoquista();
        estoquista.setNome("Estoquista 01");
        estoquista.setEmail("estoquista01@gmail.com");
        estoquista.setSenha("123456789");
        estoquista.setPerfil(ERole.ROLE_ESTOQUISTA);
        estoquistaRepository.save(estoquista);
    }

    @Test
    public void listarEstoquistas() {
        List<Estoquista> estoquistas = estoquistaRepository.findAll();
        estoquistas.forEach(System.out::println);
    }

    @Test
    public void editarEstoquistaPorEmail() {
        String email = "fornecedor01@gmail.com";
        Optional<Estoquista> optionalUsuario = estoquistaRepository.findByEmail(email);
        Estoquista estoquista = optionalUsuario.orElse(null);
        estoquista.setSenha("1234567890");
        estoquistaRepository.save(estoquista);
    }

    @Test
    public void buscarEstoquistaPorEmail() {
        String email = "fornecedor01@gmail.com";
        Optional<Estoquista> optionalFornecedor = estoquistaRepository.findByEmail(email);
        optionalFornecedor.ifPresent(System.out::println);

    }

    @Test
    public void deletarEstoquistaPorId() {
        estoquistaRepository.deleteById(1L);
    }

}
