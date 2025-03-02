package com.gianmarques.estoqueapi.repository;

import com.gianmarques.estoqueapi.entity.Fornecedor;
import jakarta.validation.constraints.Email;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
    Optional<Fornecedor> findByEmail(String email);

    Page<Fornecedor> findByEmailOrId(@Email String email, Long id, Pageable pageable);
}
