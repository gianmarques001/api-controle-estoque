package com.gianmarques.estoqueapi.repository;

import com.gianmarques.estoqueapi.entity.Fornecedor;
import com.gianmarques.estoqueapi.entity.Produto;
import com.gianmarques.estoqueapi.entity.enums.ECategoriaProduto;
import com.gianmarques.estoqueapi.repository.projection.ProdutoListProjection;
import com.gianmarques.estoqueapi.repository.projection.ProdutoProjection;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query("select p from Produto p")
    Page<ProdutoProjection> findAllPageable(Pageable pageable);

    @Query("select p from Produto p")
    Page<ProdutoListProjection> findAllPageableEstoque(Pageable pageable);


    Page<ProdutoListProjection> findAllPageableByQuantidadeGreaterThanEqual(Pageable pageable, @Min(1) Integer quantidadeIsGreaterThan);

    List<Produto> findByFornecedor(Fornecedor fornecedor);

    Page<ProdutoProjection> findProdutoByFornecedor(Pageable pageable, Fornecedor fornecedor);

    List<Produto> findAllByQuantidadeGreaterThanEqual(@Min(1) Integer quantidadeIsGreaterThan);

    List<Produto> findAllByCategoria(ECategoriaProduto categoria);

    List<Produto> findAllByNome(@Size(min = 10, max = 20) String nome);

    @Transactional
    @Modifying
    @Query("UPDATE Produto p SET p.quantidade = :quantidade WHERE p.id = :produtoId")
    void atualizarQtdProduto(@Param("produtoId") Long produtoId, @Param("quantidade") Integer quantidade);

}
