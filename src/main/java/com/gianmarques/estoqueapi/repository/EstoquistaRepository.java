package com.gianmarques.estoqueapi.repository;

import com.gianmarques.estoqueapi.entity.Estoquista;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstoquistaRepository extends JpaRepository<Estoquista, Long> {
    Optional<Estoquista> findByEmail(String email);
}
