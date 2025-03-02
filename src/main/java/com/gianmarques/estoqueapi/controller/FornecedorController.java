package com.gianmarques.estoqueapi.controller;

import com.gianmarques.estoqueapi.entity.Fornecedor;
import com.gianmarques.estoqueapi.service.FornecedorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/fornecedores")
public class FornecedorController {

    private final FornecedorService fornecedorService;

    public FornecedorController(FornecedorService fornecedorService) {
        this.fornecedorService = fornecedorService;
    }

    @GetMapping
    public ResponseEntity<List<Fornecedor>> listar() {
        List<Fornecedor> fornecedores = fornecedorService.listar();
        return ResponseEntity.ok(fornecedores);
    }

    @PostMapping
    public ResponseEntity<Fornecedor> salvar(@RequestBody Fornecedor fornecedor) {
        fornecedorService.salvar(fornecedor);
        URI url = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(fornecedor.getId())
                .toUri();
        return ResponseEntity.created(url).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fornecedor> buscarPorId(@PathVariable Long id) {
        return fornecedorService.buscarPorId(id)
                .map(fornecedor -> ResponseEntity.ok(fornecedor))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id) {
        fornecedorService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }


}
