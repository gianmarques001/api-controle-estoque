package com.gianmarques.estoqueapi.service;

import com.gianmarques.estoqueapi.entity.Fornecedor;
import com.gianmarques.estoqueapi.entity.Permissao;
import com.gianmarques.estoqueapi.exception.exceptions.EmailUnicoException;
import com.gianmarques.estoqueapi.exception.exceptions.EntidadeNaoEncontradaException;
import com.gianmarques.estoqueapi.repository.FornecedorRepository;
import com.gianmarques.estoqueapi.repository.PermissaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;
    private final ProdutoService produtoService;
    private final PasswordEncoder passwordEncoder;
    private final PermissaoRepository permissaoRepository;

    public FornecedorService(FornecedorRepository fornecedorRepository, ProdutoService produtoService, PasswordEncoder passwordEncoder, PermissaoRepository permissaoRepository) {
        this.fornecedorRepository = fornecedorRepository;
        this.produtoService = produtoService;
        this.passwordEncoder = passwordEncoder;
        this.permissaoRepository = permissaoRepository;
    }

    @Transactional
    public Fornecedor salvar(Fornecedor fornecedor) {
        try {
            Permissao roleFornecedor = permissaoRepository.findByNome("ROLE_FORNECEDOR");
            fornecedor.setPerfil(Set.of(roleFornecedor));
            fornecedor.setSenha(passwordEncoder.encode(fornecedor.getSenha()));
            return fornecedorRepository.save(fornecedor);
        } catch (DataIntegrityViolationException e) {
            throw new EmailUnicoException("Dados já existentes (E-mail ou Telefone), informe outro.");
        }
    }


    public List<Fornecedor> listar() {
        return fornecedorRepository.findAll();
    }

    public Fornecedor editarPorId(Long id, Fornecedor fornecedor) {
        Fornecedor fornecedorAntigo = buscar(id).get();
        fornecedorAntigo.setNome(fornecedor.getNome());
        fornecedorAntigo.setTelefone(fornecedor.getTelefone());
        return fornecedorRepository.save(fornecedorAntigo);
    }

    public Optional<Fornecedor> buscar(Long id) {
        return Optional.ofNullable(fornecedorRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException("Fornecedor não encontrado.")));
    }

    @Transactional
    public void deletarPorId(Long id) {
        Fornecedor fornecedor = buscar(id).get();
        fornecedor.getProdutos().forEach(produto -> {
            produtoService.deletarPorId(produto.getId(), fornecedor.getId());
        });
        fornecedorRepository.deleteById(fornecedor.getId());
    }

}
