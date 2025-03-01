package com.gianmarques.estoqueapi.service;

import com.gianmarques.estoqueapi.entity.Produto;
import com.gianmarques.estoqueapi.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IProdutoService implements IService<Produto> {


    private final ProdutoRepository produtoRepository;

    public IProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    @Override
    public List<Produto> listar() {
        return produtoRepository.findAll();
    }


    @Override
    public Produto editarPorId(Long id, Produto novoProduto) {
        Optional<Produto> produto = buscarPorId(id);
        Produto produtoSalvo = produto.get();
        produtoSalvo.setQuantidade(novoProduto.getQuantidade());
        produtoSalvo.setPreco(novoProduto.getPreco());
        return produtoRepository.save(produtoSalvo);
    }

    @Override
    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    @Override
    public void deletarPorId(Long id) {
        produtoRepository.deleteById(id);
    }



}
