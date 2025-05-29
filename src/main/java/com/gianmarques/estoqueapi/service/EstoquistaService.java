package com.gianmarques.estoqueapi.service;

import com.gianmarques.estoqueapi.entity.Estoquista;
import com.gianmarques.estoqueapi.entity.Permissao;
import com.gianmarques.estoqueapi.exception.exceptions.EmailUnicoException;
import com.gianmarques.estoqueapi.exception.exceptions.EntidadeNaoEncontradaException;
import com.gianmarques.estoqueapi.repository.EstoquistaRepository;
import com.gianmarques.estoqueapi.repository.PermissaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class EstoquistaService {

    private final EstoquistaRepository estoquistaRepository;
    private final PasswordEncoder passwordEncoder;
    private final PermissaoRepository permissaoRepository;


    public EstoquistaService(EstoquistaRepository estoquistaRepository, PasswordEncoder passwordEncoder, PermissaoRepository permissaoRepository) {
        this.estoquistaRepository = estoquistaRepository;
        this.passwordEncoder = passwordEncoder;
        this.permissaoRepository = permissaoRepository;
    }

    @Transactional
    public Estoquista salvar(Estoquista estoquista) {
        try {
            Permissao roleEstoquista = permissaoRepository.findByNome("ROLE_ESTOQUISTA");
            estoquista.setPerfil(Set.of(roleEstoquista));
            estoquista.setSenha(passwordEncoder.encode(estoquista.getSenha()));
            return estoquistaRepository.save(estoquista);
        } catch (DataIntegrityViolationException e) {
            throw new EmailUnicoException("Email já cadastrado, informe outro.");
        }
    }

    public List<Estoquista> listar() {
        return estoquistaRepository.findAll();
    }

    public Optional<Estoquista> buscar(Long id) {
        return Optional.ofNullable(estoquistaRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException("Estoquista não encontrado.")));
    }

    public void deletarPorId(Long id) {
        buscar(id).ifPresent(estoquistaRepository::delete);
    }

}
