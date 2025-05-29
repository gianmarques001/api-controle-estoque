package com.gianmarques.estoqueapi.repository;

import com.gianmarques.estoqueapi.entity.Fornecedor;
import com.gianmarques.estoqueapi.entity.Solicitacao;
import com.gianmarques.estoqueapi.repository.projection.SolicitacaoPorFornecedorProjection;
import com.gianmarques.estoqueapi.repository.projection.SolicitacaoProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long> {

    @Query("SELECT s.id AS id, s.produto.nome AS nomeProduto, s.status AS status, " +
            "s.quantidadeSolicitada AS quantidadeSolicitada, s.quantidadeAtendida AS quantidadeAtendida " +
            "FROM Solicitacao s")
    Page<SolicitacaoProjection> findAllPageableSolicitacoes(Pageable pageable);


    @Query("select s.id AS idSolicitacao, s.produto.nome AS nomeProduto, s.quantidadeSolicitada as quantidadeSolicitada, s.estoquista.nome AS nomeEstoquista" +
            " from Solicitacao as s where s.produto.fornecedor =:fornecedor and s.status = 'SOLICITADO'")
    Page<SolicitacaoPorFornecedorProjection> findAllPageableSolicitacoesByFornecedor(Pageable pageable, @Param("fornecedor") Fornecedor fornecedor);

    @Query("SELECT s FROM Solicitacao as s where s.status = 'ATENDIDO' ")
    List<Solicitacao> findAllSolicitacoesAtendidas();

    @Query("select s from Solicitacao as s WHERE s.produto.fornecedor =:fornecedor")
    List<Solicitacao> findAllSolicitacoesByFornecedor(@Param("fornecedor") Fornecedor fornecedor);

}
