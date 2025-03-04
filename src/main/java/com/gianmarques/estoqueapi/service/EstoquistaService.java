package com.gianmarques.estoqueapi.service;

import com.gianmarques.estoqueapi.entity.Estoquista;
import com.gianmarques.estoqueapi.entity.enums.EPerfil;
import com.gianmarques.estoqueapi.exception.exceptions.EmailUnicoException;
import com.gianmarques.estoqueapi.exception.exceptions.EntidadeNaoEncontradaException;
import com.gianmarques.estoqueapi.repository.EstoquistaRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstoquistaService implements GenericService<Estoquista> {

    private final EstoquistaRepository estoquistaRepository;

    public EstoquistaService(EstoquistaRepository estoquistaRepository) {
        this.estoquistaRepository = estoquistaRepository;
    }

    @Override
    public Estoquista salvar(Estoquista estoquista) {
        try {
            estoquista.setPerfil(EPerfil.ESTOQUISTA);
            return estoquistaRepository.save(estoquista);
        } catch (DataIntegrityViolationException e) {
            throw new EmailUnicoException("Email já cadastrado, informe outro.");
        }
    }

    @Override
    public List<Estoquista> listar() {
        return estoquistaRepository.findAll();
    }

    @Override
    public Estoquista editarPorId(Long id, Estoquista estoquista) {
        return null;
    }

    @Override
    public Optional<Estoquista> buscarPorId(Long id) {
        return Optional.ofNullable(estoquistaRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException("Estoquista não encontrado.")));

    }

    @Override
    public void deletarPorId(Long id) {
        buscarPorId(id).ifPresent(estoquistaRepository::delete);
    }
}
