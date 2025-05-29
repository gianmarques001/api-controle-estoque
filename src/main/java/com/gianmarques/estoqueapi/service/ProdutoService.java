package com.gianmarques.estoqueapi.service;

import com.gianmarques.estoqueapi.entity.Fornecedor;
import com.gianmarques.estoqueapi.entity.Produto;
import com.gianmarques.estoqueapi.exception.exceptions.EntidadeNaoEncontradaException;
import com.gianmarques.estoqueapi.exception.exceptions.ProdutoDuplicadoException;
import com.gianmarques.estoqueapi.repository.ProdutoRepository;
import com.gianmarques.estoqueapi.repository.projection.ProdutoPorFornecedorProjection;
import com.gianmarques.estoqueapi.repository.projection.ProdutoProjection;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private final Integer QTD_PRODUTOS = 3;

    private final ProdutoRepository produtoRepository;
    private final FornecedorService fornecedorService;

    public ProdutoService(ProdutoRepository produtoRepository, @Lazy FornecedorService fornecedorService) {
        this.produtoRepository = produtoRepository;
        this.fornecedorService = fornecedorService;
    }

    private List<Produto> getProdutosPorFornecedor(Fornecedor fornecedor) {
        return produtoRepository.findByFornecedor(fornecedor);
    }

    private Fornecedor getFornecedor(Long id) {
        Optional<Fornecedor> optionalFornecedor = fornecedorService.buscar(id);
        return optionalFornecedor.get();
    }

    public Produto salvar(Long idFornececdor, Produto produto) {
        String categoriaProduto = produto.getCategoria().toString();
        Fornecedor fornecedor = getFornecedor(idFornececdor);
        produto.setFornecedor(fornecedor);
        List<Produto> produtos = getProdutosPorFornecedor(fornecedor);

        produtos.forEach(p -> {
            if (p.getCategoria().toString().equals(categoriaProduto) && p.getNome().equalsIgnoreCase(produto.getNome())) {
                throw new ProdutoDuplicadoException("Produto Duplicado");
            }
        });
        return produtoRepository.save(produto);
    }

    public Page<ProdutoProjection> listar(Pageable pageable, Long id) {
        Fornecedor fornecedor = getFornecedor(id);
        Page<ProdutoProjection> produtosPorFornecedor = produtoRepository.findProdutoByFornecedor(pageable, fornecedor);
        fornecedor.setProdutos(getProdutosPorFornecedor(fornecedor));
        return produtosPorFornecedor;
    }

    public Page<ProdutoPorFornecedorProjection> listar(Pageable pageable, Boolean emFalta) {
        if (emFalta != null) {
            return produtoRepository.findAllPageableByQuantidadeGreaterThanEqual(pageable, QTD_PRODUTOS);
        }
        return produtoRepository.findAllPageableEstoque(pageable);
    }

    public Produto buscarPorId(Long idProduto, Long idFornecedor) {
        Fornecedor fornecedor = getFornecedor(idFornecedor);
        List<Produto> produtos = getProdutosPorFornecedor(fornecedor);
        Optional<Produto> optionalProduto = produtos.stream().filter(produto -> produto.getId() == idProduto).findFirst();
        if (optionalProduto.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Produto não encontrado");
        }
        return optionalProduto.get();
    }

    public Produto buscarPorId(Long idProduto) {
        Optional<Produto> optionalProduto = produtoRepository.findById(idProduto);
        if (optionalProduto.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Produto não encontrado");
        }
        return optionalProduto.get();
    }


    public Produto editar(Long idProduto, Long idFornecedor, Produto novoProduto) {
        Produto produto = buscarPorId(idProduto, idFornecedor);
        Produto produtoAtualizado = editarProduto(novoProduto, produto);
        return produtoRepository.save(produtoAtualizado);
    }

    private Produto editarProduto(Produto novoProduto, Produto antigoProduto) {
        antigoProduto.setQuantidade(novoProduto.getQuantidade());
        antigoProduto.setPreco(novoProduto.getPreco());
        return antigoProduto;
    }

    @Transactional
    public void deletarPorId(Long idProduto, Long idFornecedor) {
        Produto produto = buscarPorId(idProduto, idFornecedor);
        produtoRepository.delete(produto);

    }

}
