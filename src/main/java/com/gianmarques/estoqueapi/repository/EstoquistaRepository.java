package com.gianmarques.estoqueapi.repository;

import com.gianmarques.estoqueapi.entity.Estoquista;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstoquistaRepository extends JpaRepository<Estoquista, Long> {

}
