package com.gianmarques.estoqueapi.repository;

import com.gianmarques.estoqueapi.entity.Fornecedor;
import com.gianmarques.estoqueapi.entity.Produto;
import com.gianmarques.estoqueapi.entity.enums.ECategoriaProduto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByFornecedor(Fornecedor fornecedor);

    List<Produto> findAllByQuantidade(@Min(1) Integer quantidade);

    List<Produto> findAllByQuantidadeGreaterThanEqual(@Min(1) Integer quantidadeIsGreaterThan);

    List<Produto> findAllByCategoria(ECategoriaProduto categoria);

    List<Produto> findAllByNome(@Size(min = 10, max = 20) String nome);
}
