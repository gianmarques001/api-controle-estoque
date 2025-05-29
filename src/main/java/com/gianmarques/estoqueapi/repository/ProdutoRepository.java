package com.gianmarques.estoqueapi.repository;

import com.gianmarques.estoqueapi.entity.Fornecedor;
import com.gianmarques.estoqueapi.entity.Produto;
import com.gianmarques.estoqueapi.repository.projection.ProdutoPorFornecedorProjection;
import com.gianmarques.estoqueapi.repository.projection.ProdutoProjection;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {


    @Query("select p from Produto p")
    Page<ProdutoPorFornecedorProjection> findAllPageableEstoque(Pageable pageable);

    Page<ProdutoPorFornecedorProjection> findAllPageableByQuantidadeGreaterThanEqual(Pageable pageable, @Min(1) Integer quantidadeIsGreaterThan);

    List<Produto> findByFornecedor(Fornecedor fornecedor);

    Page<ProdutoProjection> findProdutoByFornecedor(Pageable pageable, Fornecedor fornecedor);


}
