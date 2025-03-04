package com.gianmarques.estoqueapi.controller;

import com.gianmarques.estoqueapi.dto.produto.ProdutoDetailsResponseDTO;
import com.gianmarques.estoqueapi.dto.produto.ProdutoRequestDTO;
import com.gianmarques.estoqueapi.dto.produto.ProdutoResponseDTO;
import com.gianmarques.estoqueapi.dto.produto.ProdutoUpdateRequestDTO;
import com.gianmarques.estoqueapi.entity.Produto;
import com.gianmarques.estoqueapi.mapper.ProdutoMapper;
import com.gianmarques.estoqueapi.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;
    private final ProdutoMapper produtoMapper;

    public ProdutoController(ProdutoService produtoService, ProdutoMapper produtoMapper) {
        this.produtoService = produtoService;
        this.produtoMapper = produtoMapper;
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> listar() {
        return ResponseEntity.ok(produtoService.listar().stream().map(produtoMapper::toDTO).toList());
    }

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> salvar(@Valid @RequestBody ProdutoRequestDTO produtoRequestDto) {
        Produto produtoSalvo = produtoService.salvar(produtoMapper.toEntity(produtoRequestDto));
        URI url = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(produtoSalvo.getId())
                .toUri();
        return ResponseEntity.created(url).body(produtoMapper.toDTO(produtoSalvo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDetailsResponseDTO> buscarPorId(@PathVariable Long id) {
        Produto produto = produtoService.buscarPorId(id).get();
        return ResponseEntity.ok(produtoMapper.toDetailsDTO(produto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id) {
        produtoService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProdutoDetailsResponseDTO> atualizarPorId(@PathVariable Long id, @Valid @RequestBody ProdutoUpdateRequestDTO produtoRequestDto) {
        Produto produtoAtualizado = produtoService.editarPorId(id, produtoMapper.toEntity(produtoRequestDto));
        return ResponseEntity.ok().body(produtoMapper.toDetailsDTO(produtoAtualizado));
    }

}
