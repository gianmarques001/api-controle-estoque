package com.gianmarques.estoqueapi;


import com.gianmarques.estoqueapi.entity.Estoquista;
import com.gianmarques.estoqueapi.entity.Fornecedor;
import com.gianmarques.estoqueapi.entity.Produto;
import com.gianmarques.estoqueapi.entity.Solicitacao;
import com.gianmarques.estoqueapi.entity.enums.EStatusSolicitacao;
import com.gianmarques.estoqueapi.repository.EstoquistaRepository;
import com.gianmarques.estoqueapi.repository.FornecedorRepository;
import com.gianmarques.estoqueapi.repository.ProdutoRepository;
import com.gianmarques.estoqueapi.repository.SolicitacaoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SolicitacaoTest {


    @Autowired
    private SolicitacaoRepository solicitacaoRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private EstoquistaRepository estoquistaRepository;
    @Autowired
    private FornecedorRepository fornecedorRepository;


    @Test
    public void listarSolicitacoes() {
        List<Solicitacao> solicitacoes = solicitacaoRepository.findAll();
        solicitacoes.forEach(System.out::println);
    }


    @Test
    public void solicitarProduto() {
        Solicitacao solicitacao = new Solicitacao();
        Produto produtoSolicitado = produtoRepository.findById(1L).get();
        Estoquista estoquista = estoquistaRepository.findById(1L).get();
        solicitacao.setProduto(produtoSolicitado);
        solicitacao.setQuantidadeSolicitada(5);
        solicitacao.setEstoquista(estoquista);
        solicitacaoRepository.save(solicitacao);
    }


    @Test
    public void buscarSolicitacoesFornecedor() {
        Fornecedor fornecedor = fornecedorRepository.findById(1L).get();
        List<Solicitacao> solicitacaos = solicitacaoRepository.buscarPorProdutoPorFornecedor(fornecedor);
        List<Solicitacao> solicitacoesAbertas = solicitacaos.stream().filter(solicitacao -> solicitacao.getStatus().equals(EStatusSolicitacao.SOLICITADO)).toList();
        solicitacoesAbertas.forEach(System.out::println);
    }

    @Test
    public void reporProduto() {
        // Id da Solicitacao, Qtd Atendida
        Solicitacao solicitacao = solicitacaoRepository.findById(6L).get();

        Integer QTD_ATENDIDA = 2;
        solicitacao.setQuantidadeAtendida(QTD_ATENDIDA);
        solicitacao.getProduto().setQuantidade(solicitacao.getProduto().getQuantidade() + QTD_ATENDIDA);
        solicitacao.setStatus(EStatusSolicitacao.ATENDIDO);
        produtoRepository.save(solicitacao.getProduto());
        solicitacaoRepository.save(solicitacao);
    }


    @Test
    public void reporProduto2() {
        // Id da Solicitacao, Qtd Atendida
        Solicitacao solicitacao = solicitacaoRepository.findById(1L).get();
        Integer QTD_ATENDIDA = 1;
        solicitacao.setQuantidadeAtendida(QTD_ATENDIDA);
        solicitacao.setStatus(EStatusSolicitacao.ATENDIDO);
        produtoRepository.atualizarQtdProduto(solicitacao.getProduto().getId(), solicitacao.getProduto().getQuantidade() + QTD_ATENDIDA);
        solicitacaoRepository.save(solicitacao);
    }


    @Test
    public void reporProduto3() {
        // Id da Solicitacao, Qtd Atendida
        Solicitacao solicitacao = solicitacaoRepository.findById(6L).get();
        Integer QTD_ATENDIDA = 2;
        atualizarProduto(solicitacao, QTD_ATENDIDA);
        salvarSolicitacao(solicitacao);
    }

    private void atualizarProduto(Solicitacao solicitacao, Integer QTD_ATENDIDA) {
        solicitacao.setQuantidadeAtendida(QTD_ATENDIDA);
        Produto produto = solicitacao.getProduto();
        produto.setQuantidade(produto.getQuantidade() + QTD_ATENDIDA);
        solicitacao.setStatus(EStatusSolicitacao.ATENDIDO);
    }

    private void salvarSolicitacao(Solicitacao solicitacao) {
        produtoRepository.save(solicitacao.getProduto());
        solicitacaoRepository.save(solicitacao);
    }


    @Test
    public void reporProduto4() {
        // Id da Solicitacao, Qtd Atendida
        Solicitacao solicitacao = solicitacaoRepository.findById(1L).get();
        Integer QTD_ATENDIDA = 1;
        atualizarSolicitacao(solicitacao, QTD_ATENDIDA);
        salvarSolicitacao2(solicitacao);
    }

    private void atualizarSolicitacao(Solicitacao solicitacao, Integer QTD_ATENDIDA) {
        solicitacao.setQuantidadeAtendida(QTD_ATENDIDA);
        Produto produto = solicitacao.getProduto();
        produtoRepository.atualizarQtdProduto(produto.getId(), produto.getQuantidade() + QTD_ATENDIDA);
        solicitacao.setStatus(EStatusSolicitacao.ATENDIDO);
    }

    private void salvarSolicitacao2(Solicitacao solicitacao) {
        solicitacaoRepository.save(solicitacao);
    }


    @Test
    public void confirmarSolicitacao() {
        Solicitacao solicitacao = solicitacaoRepository.findById(6L).get();
        if (solicitacao.getQuantidadeAtendida() >= solicitacao.getQuantidadeSolicitada()) {
            solicitacao.setStatus(EStatusSolicitacao.FINALIZADO);
            solicitacaoRepository.save(solicitacao);
        } else {
            throw new RuntimeException("QUANTIDADE ATENDIDA INFERIOR A SOLICITADA");
        }

    }


//    public void comprarProduto() {
//
//        Carrinho carrinho = new Carrinho();
//        Map<Produto, Integer> produtos = new HashMap<>();
//
//        Produto produto = produtoRepository.findById(1L).get();
//        produtos.put(produto, 1);
//
//        produtoRepository.atualizarQtdProduto(produto.getId(), produto.getQuantidade() - 1);
//        carrinho.setProdutos(produtos);
//        carrinhoRepository.save(carrinho);
//    }


}
