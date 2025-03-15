package com.gianmarques.estoqueapi.controller;

import com.gianmarques.estoqueapi.dto.fornecedor.FornecedorDetailsResponseDTO;
import com.gianmarques.estoqueapi.dto.fornecedor.FornecedorRequestDTO;
import com.gianmarques.estoqueapi.dto.fornecedor.FornecedorResponseDTO;
import com.gianmarques.estoqueapi.dto.fornecedor.FornecedorUpdateRequestDTO;
import com.gianmarques.estoqueapi.entity.Fornecedor;
import com.gianmarques.estoqueapi.mapper.FornecedorMapper;
import com.gianmarques.estoqueapi.service.FornecedorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/fornecedores")

public class FornecedorController {

    private final FornecedorService fornecedorService;
    private final FornecedorMapper fornecedorMapper;


    public FornecedorController(FornecedorService fornecedorService, FornecedorMapper fornecedorMapper) {
        this.fornecedorService = fornecedorService;
        this.fornecedorMapper = fornecedorMapper;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<FornecedorResponseDTO>> listar() {
        List<FornecedorResponseDTO> fornecedores = fornecedorService.listar().stream().map(fornecedorMapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(fornecedores);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<FornecedorResponseDTO> salvar(@Valid @RequestBody FornecedorRequestDTO fornecedorRequestDTO) {
        Fornecedor fornecedorSalvo = fornecedorService.salvar(fornecedorMapper.toEntity(fornecedorRequestDTO));
        URI url = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(fornecedorSalvo.getId())
                .toUri();
        return ResponseEntity.created(url).body(fornecedorMapper.toDTO(fornecedorSalvo));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ESTOQUISTA')")
    @GetMapping("/{id}")
    public ResponseEntity<FornecedorDetailsResponseDTO> buscarPorId(@PathVariable Long id) {
        Fornecedor fornecedor = fornecedorService.buscarPorId(id).get();
        return ResponseEntity.ok(fornecedorMapper.toDetailsDTO(fornecedor));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id) {
        fornecedorService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<FornecedorDetailsResponseDTO> atualizarPorId(@PathVariable Long id,  @Valid @RequestBody FornecedorUpdateRequestDTO fornecedorUpdateRequestDTO) {
        Fornecedor fornecedorAtualizado = fornecedorService.editarPorId(id, fornecedorMapper.toEntity(fornecedorUpdateRequestDTO));
        return ResponseEntity.ok().body(fornecedorMapper.toDetailsDTO(fornecedorAtualizado));
    }


}
