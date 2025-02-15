package com.gianmarques.estoqueapi.repository;

import com.gianmarques.estoqueapi.entity.Fornecedor;
import com.gianmarques.estoqueapi.entity.Solicitacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long> {


    @Query("select s from Solicitacao as s where s.produto.fornecedor =:fornecedor")
    List<Solicitacao> buscarPorProdutoPorFornecedor(@Param("fornecedor") Fornecedor fornecedor);
}
