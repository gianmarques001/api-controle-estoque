package com.gianmarques.estoqueapi.controller;

import com.gianmarques.estoqueapi.dto.estoquista.EstoquistaRequestDTO;
import com.gianmarques.estoqueapi.dto.estoquista.EstoquistaResponseDTO;
import com.gianmarques.estoqueapi.entity.Estoquista;
import com.gianmarques.estoqueapi.mapper.EstoquistaMapper;
import com.gianmarques.estoqueapi.service.EstoquistaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/estoquistas")
public class EstoquistaController {

    private final EstoquistaService estoquistaService;
    private final EstoquistaMapper estoquistaMapper;

    public EstoquistaController(EstoquistaService estoquistaService, EstoquistaMapper estoquistaMapper) {
        this.estoquistaService = estoquistaService;
        this.estoquistaMapper = estoquistaMapper;
    }


    @GetMapping
    private ResponseEntity<List<EstoquistaResponseDTO>> listar() {
        List<EstoquistaResponseDTO> estoquistas = estoquistaService.listar().stream().map(estoquistaMapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(estoquistas);
    }

    @PostMapping
    public ResponseEntity<EstoquistaResponseDTO> salvar(@Valid @RequestBody EstoquistaRequestDTO estoquistaRequestDTO) {
        Estoquista estoquistaSalvo = estoquistaService.salvar(estoquistaMapper.toEntity(estoquistaRequestDTO));

        URI url = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(estoquistaSalvo.getId())
                .toUri();
        return ResponseEntity.created(url).body(estoquistaMapper.toDTO(estoquistaSalvo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstoquistaResponseDTO> buscarPorId(@PathVariable Long id) {
        Estoquista estoquista = estoquistaService.buscarPorId(id).get();
        return ResponseEntity.ok(estoquistaMapper.toDTO(estoquista));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id) {
        estoquistaService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }

}
