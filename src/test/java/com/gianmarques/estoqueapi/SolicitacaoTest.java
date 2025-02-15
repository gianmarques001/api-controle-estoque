package com.gianmarques.estoqueapi;


import com.gianmarques.estoqueapi.entity.*;
import com.gianmarques.estoqueapi.entity.enums.EPerfil;
import com.gianmarques.estoqueapi.entity.enums.EStatusSolicitacao;
import com.gianmarques.estoqueapi.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

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
    @Autowired
    private UsuarioRepository usuarioRepository;


    @Test
    public void listarSolicitacoes() {
        List<Solicitacao> solicitacoes = solicitacaoRepository.findAll();
        solicitacoes.forEach(System.out::println);
    }


    @Test
    public void solicitarProduto() {
        Solicitacao solicitacao = new Solicitacao();
        Produto produtoSolicitado = produtoRepository.findById(1L).get();
        Estoquista estoquista = estoquistaRepository.findById(11L).get();
        solicitacao.setProduto(produtoSolicitado);
        solicitacao.setQuantidadeSolicitada(2);
        solicitacao.setEstoquista(estoquista);
        solicitacaoRepository.save(solicitacao);
    }

    public void darBaixa(){
        Solicitacao solicitacao = solicitacaoRepository.findById(1L).get();
        solicitacao.setStatus(EStatusSolicitacao.FINALIZADO);
        solicitacaoRepository.save(solicitacao);
    }

    @Test
    public void buscarSolicitacoesFornecedor() {
        Fornecedor fornecedor = fornecedorRepository.findById(1L).get();
        List<Solicitacao> solicitacaos = solicitacaoRepository.buscarPorProdutoPorFornecedor(fornecedor);
        List<Solicitacao> solicitacoesAbertas = solicitacaos.stream().filter(solicitacao -> solicitacao.getStatus().equals(EStatusSolicitacao.SOLICITADO)).toList();

        solicitacaos.get(0).setQuantidadeAtendida(2);
        Integer quantidade = solicitacaos.get(0).getProduto().getQuantidade();
        Integer quantidadeAtendida = 2;
        System.out.println("QTD" + quantidade);
        solicitacaos.get(0).getProduto().setQuantidade(quantidade + quantidadeAtendida);
        System.out.println("QTD DPS" + solicitacaos.get(0).getProduto().getQuantidade());
        solicitacaos.get(0).setStatus(EStatusSolicitacao.ATENDIDO);
        produtoRepository.save(solicitacaos.get(0).getProduto());
        solicitacaoRepository.save(solicitacaos.get(0));
    }




//    public void comprarProduto() {
//        Usuario usuario = new Usuario();
//        Optional<Usuario> usuarioOptional = usuarioRepository.findByPerfil(EPerfil.CLIENTE);
//        Usuario usuarioAtual = usuarioOptional.get();
//
//
//        Carrinho carrinho = new Carrinho();
//        carrinho.setUsuario(usuario);
//
//        Map<Produto, Integer> produtos = new HashMap<>();
//
//        produtos.put(produto1, 1);
//
//        carrinho.setProdutos(produtos);
//        carrinhoRepository.save(carrinho);
//    }


}
