package com.gianmarques.estoqueapi;


import com.gianmarques.estoqueapi.entity.Fornecedor;
import com.gianmarques.estoqueapi.entity.Produto;
import com.gianmarques.estoqueapi.repository.FornecedorRepository;
import com.gianmarques.estoqueapi.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class FornecedorTest {

    @Autowired
    private FornecedorRepository fornecedorRepository;
    @Autowired
    private ProdutoRepository produtoRepository;

    @Test
    public void salvarFornecedor() {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome("Fornecedor 01");
        fornecedor.setEmail("fornecedor01@gmail.com");
        fornecedor.setTelefone("999999998");
        fornecedor.setSenha("123456789");
        fornecedorRepository.save(fornecedor);
    }

    @Test
    public void listarFornecedores() {
        List<Fornecedor> fornecedores = fornecedorRepository.findAll();
        fornecedores.forEach(System.out::println);
    }

    @Test
    public void editarFornecedorPorEmail() {
        String email = "fornecedor01@gmail.com";
        Optional<Fornecedor> optionalUsuario = fornecedorRepository.findByEmail(email);
        Fornecedor fornecedor = optionalUsuario.orElse(null);
        fornecedor.setSenha("1234567890");
        fornecedorRepository.save(fornecedor);
    }

    @Test
    public void buscarFornecedorPorEmail() {
        String email = "fornecedor01@gmail.com";
        Optional<Fornecedor> optionalFornecedor = fornecedorRepository.findByEmail(email);
        optionalFornecedor.ifPresent(System.out::println);

    }

    @Test
    public void deletarFornecedorPorId() {
        fornecedorRepository.deleteById(1L);
    }


    @Test
    public void listarProdutosPorFornecedor() {
        Optional<Fornecedor> fornecedorOptional = fornecedorRepository.findById(1L);
        Fornecedor fornecedor = fornecedorOptional.get();
        List<Produto> produtos = produtoRepository.findByFornecedor(fornecedor);
        fornecedor.setProdutos(produtos);
        fornecedor.getProdutos().forEach(System.out::println);
    }
}
