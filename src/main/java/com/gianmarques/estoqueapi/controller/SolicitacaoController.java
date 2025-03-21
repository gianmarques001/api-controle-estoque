package com.gianmarques.estoqueapi.controller;


import com.gianmarques.estoqueapi.dto.produto.ProdutoUpdateRequestDTO;
import com.gianmarques.estoqueapi.dto.solicitacao.SolicitacaoRequestDTO;
import com.gianmarques.estoqueapi.mapper.GenericMapper;
import com.gianmarques.estoqueapi.mapper.ProdutoMapper;
import com.gianmarques.estoqueapi.mapper.SolicitacaoMapper;
import com.gianmarques.estoqueapi.repository.pageable.GenericPageable;
import com.gianmarques.estoqueapi.repository.projection.SolicitacaoFornecedorProjection;
import com.gianmarques.estoqueapi.repository.projection.SolicitacaoListProjection;
import com.gianmarques.estoqueapi.security.jwt.JwtUserDetails;
import com.gianmarques.estoqueapi.service.SolicitacaoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/solicitacoes")
public class SolicitacaoController {

    private final SolicitacaoService solicitacaoService;
    private final SolicitacaoMapper solicitacaoMapper;
    private final GenericMapper genericMapper;
    private final ProdutoMapper produtoMapper;

    public SolicitacaoController(SolicitacaoService solicitacaoService, SolicitacaoMapper solicitacaoMapper, GenericMapper genericMapper, ProdutoMapper produtoMapper) {
        this.solicitacaoService = solicitacaoService;
        this.solicitacaoMapper = solicitacaoMapper;
        this.genericMapper = genericMapper;
        this.produtoMapper = produtoMapper;
    }

    @PreAuthorize("hasRole('ESTOQUISTA')")
    @PostMapping
    public ResponseEntity<Void> salvar(@AuthenticationPrincipal JwtUserDetails jwtUserDetails, @Valid @RequestBody SolicitacaoRequestDTO solicitacaoRequestDTO) {
        solicitacaoService.salvar(solicitacaoMapper.toEntity(solicitacaoRequestDTO), jwtUserDetails.getId());
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ESTOQUISTA')")
    @GetMapping
    public ResponseEntity<GenericPageable> listar(Pageable pageable) {
        Page<SolicitacaoListProjection> solicitacoes = solicitacaoService.listar(pageable);
        return ResponseEntity.ok(genericMapper.toListPageableDTO(solicitacoes));
    }

    @PreAuthorize("hasRole('FORNECEDOR')")
    @GetMapping("/fornecedores")
    public ResponseEntity<GenericPageable> listar(@AuthenticationPrincipal JwtUserDetails jwtUserDetails, Pageable pageable) {
        Page<SolicitacaoFornecedorProjection> solicitacoesEmAberto = solicitacaoService.listar(pageable, jwtUserDetails.getId());
        return ResponseEntity.ok(solicitacaoMapper.toPageableFornecedor(solicitacoesEmAberto));
    }

    @PreAuthorize("hasRole('FORNECEDOR')")
    @PatchMapping("/fornecedores/{id}")
    public ResponseEntity<Void> atualizarSolicitacao(@AuthenticationPrincipal JwtUserDetails jwtUserDetails, @PathVariable Long id, @Valid @RequestBody ProdutoUpdateRequestDTO produtoUpdateRequestDTO) {
        solicitacaoService.atualizarSolicitacao(id, jwtUserDetails.getId(), produtoMapper.toEntity(produtoUpdateRequestDTO));
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ESTOQUISTA')")
    @PatchMapping("/baixa")
    public ResponseEntity<?> atualizarSolicitacao() {
        Map<String, Integer> stringIntegerMap = solicitacaoService.buscarPorId();
        return ResponseEntity.ok(stringIntegerMap);
    }

}
