package com.gianmarques.estoqueapi.controller;

import com.gianmarques.estoqueapi.dto.produto.*;
import com.gianmarques.estoqueapi.entity.Produto;
import com.gianmarques.estoqueapi.mapper.ProdutoMapper;
import com.gianmarques.estoqueapi.repository.pageable.GenericPageable;
import com.gianmarques.estoqueapi.repository.projection.ProdutoListProjection;
import com.gianmarques.estoqueapi.repository.projection.ProdutoProjection;
import com.gianmarques.estoqueapi.security.jwt.JwtUserDetails;
import com.gianmarques.estoqueapi.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;
    private final ProdutoMapper produtoMapper;

    public ProdutoController(ProdutoService produtoService, ProdutoMapper produtoMapper) {
        this.produtoService = produtoService;
        this.produtoMapper = produtoMapper;
    }


    @PreAuthorize("hasRole('ESTOQUISTA')")
    @GetMapping
    public ResponseEntity<GenericPageable> listarProdutos(@PageableDefault(size = 5, direction = Sort.Direction.ASC) Pageable pageable, @RequestParam(required = false) Boolean emFalta) {
        Page<ProdutoListProjection> produtoProjections = produtoService.listar(pageable, emFalta);
        return ResponseEntity.ok(produtoMapper.toListPageableDTO(produtoProjections));
    }


    @PreAuthorize("hasRole('FORNECEDOR')")
    @GetMapping("/fornecedor")
    public ResponseEntity<GenericPageable> listar(@PageableDefault(size = 5, direction = Sort.Direction.ASC) @AuthenticationPrincipal JwtUserDetails jwtUserDetails, Pageable pageable) {
        Page<ProdutoProjection> produtos = produtoService.listar(pageable, jwtUserDetails.getId());
        return ResponseEntity.ok(produtoMapper.toPageableDTO(produtos));
    }

    @PreAuthorize("hasRole('FORNECEDOR')")
    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> salvar(@AuthenticationPrincipal JwtUserDetails jwtUserDetails, @Valid @RequestBody ProdutoRequestDTO produtoRequestDto) {
        Produto produtoSalvo = produtoService.salvar(produtoMapper.toEntity(produtoRequestDto), jwtUserDetails.getId());
        URI url = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(produtoSalvo.getId())
                .toUri();
        return ResponseEntity.created(url).body(produtoMapper.toDTO(produtoSalvo));
    }

    @PreAuthorize("hasRole('FORNECEDOR')")
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDetailsResponseDTO> buscarPorId(@AuthenticationPrincipal JwtUserDetails jwtUserDetails, @PathVariable Long id) {
        Long idFornecedor = jwtUserDetails.getId();
        Produto produto = produtoService.buscarPorId(id, idFornecedor).get();
        return ResponseEntity.ok(produtoMapper.toDetailsDTO(produto));
    }

    @PreAuthorize("hasRole('FORNECEDOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@AuthenticationPrincipal JwtUserDetails jwtUserDetails, @PathVariable Long id) {
        Long idFornecedor = jwtUserDetails.getId();
        produtoService.deletarPorId(id, idFornecedor);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('FORNECEDOR')")
    @PatchMapping("/{id}")
    public ResponseEntity<ProdutoUpdateResponseDTO> atualizarPorId(@PathVariable Long id, @AuthenticationPrincipal JwtUserDetails jwtUserDetails, @Valid @RequestBody ProdutoUpdateRequestDTO produtoRequestDto) {
        Long idFornecedor = jwtUserDetails.getId();
        Produto produtoAtualizado = produtoService.editarPorId(id, idFornecedor, produtoMapper.toEntity(produtoRequestDto));
        return ResponseEntity.ok().body(produtoMapper.toUpdateDTO(produtoAtualizado));
    }

}
