package com.gianmarques.estoqueapi.repository;

import com.gianmarques.estoqueapi.entity.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissaoRepository extends JpaRepository<Permissao, Long> {

    Permissao findByNome(String nome);
}
