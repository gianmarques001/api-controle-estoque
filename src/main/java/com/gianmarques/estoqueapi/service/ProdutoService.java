package com.gianmarques.estoqueapi.service;

import com.gianmarques.estoqueapi.entity.Fornecedor;
import com.gianmarques.estoqueapi.entity.Produto;
import com.gianmarques.estoqueapi.exception.exceptions.EntidadeNaoEncontradaException;
import com.gianmarques.estoqueapi.exception.exceptions.ProdutoDuplicadoException;
import com.gianmarques.estoqueapi.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService implements GenericService<Produto> {


    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public Produto salvar(Produto produto) {
        String categoriaProduto = produto.getCategoria().toString();
        String nomeProduto = produto.getNome();

        Fornecedor fornecedor = produto.getFornecedor();
       // List<Produto> produtos = produtoRepository.findByFornecedor(fornecedor);
        List<Produto> produtos = listar();
        produtos.forEach(p -> {
            if (p.getCategoria().toString().equals(categoriaProduto) && p.getNome().equalsIgnoreCase(nomeProduto)){
                throw new ProdutoDuplicadoException("Produto Duplicado");
            }
        });


        return produtoRepository.save(produto);
    }

    @Override
    public List<Produto> listar() {
        return produtoRepository.findAll();
    }


    @Override
    public Produto editarPorId(Long id, Produto novoProduto) {
        Optional<Produto> optionalProduto = Optional.of(buscarPorId(id).orElseThrow(() -> new EntidadeNaoEncontradaException("Produto não encontrado.")));
        Produto produtoAtualizado = editarProduto(novoProduto, optionalProduto.get());
        return produtoRepository.save(produtoAtualizado);
    }

    private Produto editarProduto(Produto novoProduto, Produto antigoProduto) {
        antigoProduto.setQuantidade(novoProduto.getQuantidade());
        antigoProduto.setPreco(novoProduto.getPreco());
        return antigoProduto;
    }

    @Override
    public Optional<Produto> buscarPorId(Long id) {
        return Optional.ofNullable(produtoRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException("Produto não encontrado.")));
    }

    @Override
    public void deletarPorId(Long id) {
        buscarPorId(id).ifPresent(produtoRepository::delete);
    }


}
