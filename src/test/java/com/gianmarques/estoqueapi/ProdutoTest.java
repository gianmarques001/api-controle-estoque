package com.gianmarques.estoqueapi;

import com.gianmarques.estoqueapi.entity.Fornecedor;
import com.gianmarques.estoqueapi.entity.Produto;
import com.gianmarques.estoqueapi.entity.enums.ECategoriaProduto;
import com.gianmarques.estoqueapi.repository.FornecedorRepository;
import com.gianmarques.estoqueapi.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ProdutoTest {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;


    @Test
    public void salvarProduto() {
        Produto produto = new Produto();
        produto.setNome("Sabão em Pó");
        produto.setDescricao("Sabão em Pó Brilhante");
        produto.setCategoria(ECategoriaProduto.LIMPEZA);
        produto.setQuantidade(20);
        Float preco = 12F;
        produto.setPreco(preco);
        Optional<Fornecedor> fornecedor = fornecedorRepository.findById(1L);
        produto.setFornecedor(fornecedor.get());
        produtoRepository.save(produto);
    }

    @Test
    public void listarProdutos() {
        List<Produto> produtos = produtoRepository.findAll();
        produtos.forEach(System.out::println);
    }

    @Test
    public void listarProdutosPorQuantidade() {
        List<Produto> produtos = produtoRepository.findAllByQuantidadeGreaterThanEqual(10);
        produtos.forEach(System.out::println);
    }


    @Test
    public void listarProdutosPorNome() {
        Produto produto = new Produto();
        produto.setNome("Dete");
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withIgnoreNullValues().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Produto> produtoExample = Example.of(produto, matcher);
        List<Produto> produtos = produtoRepository.findAll(produtoExample);
        produtos.forEach(System.out::println);
    }

    @Test
    public void listarProdutosPorCategoria() {
        List<Produto> produtos = produtoRepository.findAllByCategoria(ECategoriaProduto.LIMPEZA);
        produtos.forEach(System.out::println);

    }
}
