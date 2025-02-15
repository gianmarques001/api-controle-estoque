package com.gianmarques.estoqueapi;


import com.gianmarques.estoqueapi.entity.Estoquista;
import com.gianmarques.estoqueapi.entity.Solicitacao;
import com.gianmarques.estoqueapi.entity.enums.EPerfil;
import com.gianmarques.estoqueapi.repository.EstoquistaRepository;
import com.gianmarques.estoqueapi.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EstoquistaTest {

    @Autowired
    private EstoquistaRepository estoquistaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void salvarEstoquista(){
        Estoquista estoquista = new Estoquista();
        estoquista.setNome("Estoquista 01");
        estoquista.setEmail("estoquista01@gmail.com");
        estoquista.setSenha("123456789");
        estoquista.setPerfil(EPerfil.ESTOQUISTA);
        estoquistaRepository.save(estoquista);
    }



}
