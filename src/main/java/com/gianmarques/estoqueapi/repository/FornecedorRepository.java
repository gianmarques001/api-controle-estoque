package com.gianmarques.estoqueapi.repository;

import com.gianmarques.estoqueapi.entity.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
    Optional<Fornecedor> findByEmail(String email);
}
