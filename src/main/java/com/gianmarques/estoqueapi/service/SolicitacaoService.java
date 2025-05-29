package com.gianmarques.estoqueapi.service;

import com.gianmarques.estoqueapi.entity.Estoquista;
import com.gianmarques.estoqueapi.entity.Fornecedor;
import com.gianmarques.estoqueapi.entity.Produto;
import com.gianmarques.estoqueapi.entity.Solicitacao;
import com.gianmarques.estoqueapi.entity.enums.StatusSolicitacao;
import com.gianmarques.estoqueapi.exception.exceptions.EntidadeNaoEncontradaException;
import com.gianmarques.estoqueapi.repository.SolicitacaoRepository;
import com.gianmarques.estoqueapi.repository.projection.SolicitacaoPorFornecedorProjection;
import com.gianmarques.estoqueapi.repository.projection.SolicitacaoProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SolicitacaoService {

    final String SOLICITACOE_FINALIZADAS = "solicitacoesFinalizadas";
    final String SOLICITACOE_PENDENTES = "solicitacoesPendentes";

    private final SolicitacaoRepository solicitacaoRepository;
    private final EstoquistaService estoquistaService;
    private final FornecedorService fornecedorService;
    private final ProdutoService produtoService;

    public SolicitacaoService(SolicitacaoRepository solicitacaoRepository, EstoquistaService estoquistaService, FornecedorService fornecedorService, ProdutoService produtoService) {
        this.solicitacaoRepository = solicitacaoRepository;
        this.estoquistaService = estoquistaService;
        this.fornecedorService = fornecedorService;
        this.produtoService = produtoService;
    }

    public Solicitacao salvar(Long id, Solicitacao solicitacao) {
        Estoquista estoquista = estoquistaService.buscar(id).get();
        solicitacao.setEstoquista(estoquista);
        return solicitacaoRepository.save(solicitacao);
    }

    public void editar(Long idSolicitacao, Long idFornecedor, Produto produto) {
        Fornecedor fornecedor = fornecedorService.buscar(idFornecedor).get();
        List<Solicitacao> solicitacaos = solicitacaoRepository.findAllSolicitacoesByFornecedor(fornecedor);
        Optional<Solicitacao> optionalSolicitacao = solicitacaos.stream().filter(s -> s.getId().equals(idSolicitacao)).findFirst();


        if (optionalSolicitacao.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Solicitacao não encontrada");
        }

        Solicitacao solicitacao = optionalSolicitacao.get();
        atualizarProduto(solicitacao, produto.getQuantidade(), idFornecedor);
        salvarSolicitacao(solicitacao, produto.getQuantidade());
    }

    public Page<SolicitacaoPorFornecedorProjection> listar(Pageable pageable, Long id) {
        Fornecedor fornecedor = fornecedorService.buscar(id).get();
        return solicitacaoRepository.findAllPageableSolicitacoesByFornecedor(pageable, fornecedor);
    }

    public Page<SolicitacaoProjection> listar(Pageable pageable) {
        return solicitacaoRepository.findAllPageableSolicitacoes(pageable);
    }

    private void atualizarProduto(Solicitacao solicitacao, Integer quantidade, Long idFornecedor) {
        Produto produto = solicitacao.getProduto();
        produto.setQuantidade(produto.getQuantidade() + quantidade);
        produtoService.editar(solicitacao.getProduto().getId(), idFornecedor, produto);
    }

    public Map<String, Integer> concluirSolicitacao() {
        List<Solicitacao> solicitacoesAtendidas = solicitacaoRepository.findAllSolicitacoesAtendidas();
        return darBaixa(solicitacoesAtendidas);
    }

    private Map<String, Integer> darBaixa(List<Solicitacao> solicitacaos) {
        int solicitacoesAtendidasSucesso = 0;
        int solicitacoesAtendidasNaoSucesso = 0;
        for (Solicitacao solicitacao : solicitacaos) {
            if (solicitacao.getQuantidadeAtendida() >= solicitacao.getQuantidadeSolicitada()) {
                solicitacoesAtendidasSucesso++;
                solicitacao.setStatus(StatusSolicitacao.FINALIZADO);
            } else {
                solicitacoesAtendidasNaoSucesso++;
            }
        }
        Map<String, Integer> resposta = new HashMap<>();
        resposta.put(SOLICITACOE_FINALIZADAS, solicitacoesAtendidasSucesso);
        resposta.put(SOLICITACOE_PENDENTES, solicitacoesAtendidasNaoSucesso);
        return resposta;
    }

    private void salvarSolicitacao(Solicitacao solicitacao, Integer quantidade) {
        solicitacao.setQuantidadeAtendida(quantidade);
        solicitacao.setStatus(StatusSolicitacao.ATENDIDO);
        solicitacaoRepository.save(solicitacao);
    }


}
