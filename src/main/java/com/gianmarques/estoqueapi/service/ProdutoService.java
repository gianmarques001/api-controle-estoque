package com.gianmarques.estoqueapi.service;

import com.gianmarques.estoqueapi.entity.Fornecedor;
import com.gianmarques.estoqueapi.entity.Produto;
import com.gianmarques.estoqueapi.exception.exceptions.EntidadeNaoEncontradaException;
import com.gianmarques.estoqueapi.exception.exceptions.ProdutoDuplicadoException;
import com.gianmarques.estoqueapi.repository.ProdutoRepository;
import com.gianmarques.estoqueapi.repository.projection.ProdutoListProjection;
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

    private List<Produto> getProdutosByFornecedor(Fornecedor fornecedor) {
        return produtoRepository.findByFornecedor(fornecedor);
    }

    private Fornecedor getFornecedor(Long id) {
        Optional<Fornecedor> optionalFornecedor = fornecedorService.buscarPorId(id);
        return optionalFornecedor.get();
    }

    public Produto salvar(Produto produto, Long idFornececdor) {
        String categoriaProduto = produto.getCategoria().toString();
        String nomeProduto = produto.getNome();
        Fornecedor fornecedor = getFornecedor(idFornececdor);
        produto.setFornecedor(fornecedor);
        List<Produto> produtos = getProdutosByFornecedor(fornecedor);

        produtos.forEach(p -> {
            if (p.getCategoria().toString().equals(categoriaProduto) && p.getNome().equalsIgnoreCase(nomeProduto)) {
                throw new ProdutoDuplicadoException("Produto Duplicado");
            }
        });

        return produtoRepository.save(produto);
    }


    // Método Antigo
//    public List<Produto> listar(Long id) {
//        Fornecedor fornecedor = getFornecedor(id);
//        return getProdutosByFornecedor(fornecedor);
//    }

    public Page<ProdutoProjection> listar(Pageable pageable, Long id) {
        Fornecedor fornecedor = getFornecedor(id);
        Page<ProdutoProjection> produtoByFornecedor = produtoRepository.findProdutoByFornecedor(pageable, fornecedor);
        fornecedor.setProdutos(getProdutosByFornecedor(fornecedor));
        return produtoByFornecedor;
    }

    // Estoquista
    public Page<ProdutoListProjection> listar(Pageable pageable, Boolean emFalta) {
        if (emFalta != null) {
            return produtoRepository.findAllPageableByQuantidadeGreaterThanEqual(pageable, QTD_PRODUTOS);

        }
        return produtoRepository.findAllPageableEstoque(pageable);
    }

    public Optional<Produto> buscarPorId(Long idProduto, Long idFornecedor) {
        Fornecedor fornecedor = getFornecedor(idFornecedor);
        List<Produto> produtos = getProdutosByFornecedor(fornecedor);
        Optional<Produto> optionalProduto = produtos.stream().filter(produto -> produto.getId() == idProduto).findFirst();
        if (optionalProduto.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Produto não encontrado");
        }
        return optionalProduto;
    }


    public Produto editarPorId(Long idProduto, Long idFornecedor, Produto novoProduto) {
        Optional<Produto> optionalProduto = buscarPorId(idProduto, idFornecedor);
        Produto produtoAtualizado = editarProduto(novoProduto, optionalProduto.get());
        return produtoRepository.save(produtoAtualizado);
    }

    private Produto editarProduto(Produto novoProduto, Produto antigoProduto) {
        antigoProduto.setQuantidade(novoProduto.getQuantidade());
        antigoProduto.setPreco(novoProduto.getPreco());
        return antigoProduto;
    }


    @Transactional
    public void deletarPorId(Long idProduto, Long idFornecedor) {
        Optional<Produto> optionalProduto = buscarPorId(idProduto, idFornecedor);
        optionalProduto.ifPresent(produtoRepository::delete);
    }

}
