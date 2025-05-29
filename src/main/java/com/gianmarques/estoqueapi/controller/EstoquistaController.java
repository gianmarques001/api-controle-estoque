package com.gianmarques.estoqueapi.controller;

import com.gianmarques.estoqueapi.dto.estoquista.EstoquistaRequestDTO;
import com.gianmarques.estoqueapi.dto.estoquista.EstoquistaResponseDTO;
import com.gianmarques.estoqueapi.entity.Estoquista;
import com.gianmarques.estoqueapi.exception.model.ErrorMessage;
import com.gianmarques.estoqueapi.mapper.EstoquistaMapper;
import com.gianmarques.estoqueapi.service.EstoquistaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/estoquistas")
@Tag(name = "Estoquistas", description = "Controller para gerenciar estoquistas. (Apenas para Admins)")
public class EstoquistaController {

    private final EstoquistaService estoquistaService;
    private final EstoquistaMapper estoquistaMapper;

    public EstoquistaController(EstoquistaService estoquistaService, EstoquistaMapper estoquistaMapper) {
        this.estoquistaService = estoquistaService;
        this.estoquistaMapper = estoquistaMapper;
    }

    @Operation(summary = "Listar estoquistas", description = "Endpoint para listar estoquistas no sistema.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista dos estoquistas.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EstoquistaResponseDTO.class))),
                    @ApiResponse(responseCode = "401", description = "Acesso não autorizado.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso proibido.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<EstoquistaResponseDTO>> listar() {
        return ResponseEntity.ok(estoquistaService.listar().stream().map(estoquistaMapper::toDTO).collect(Collectors.toList()));
    }

    @Operation(summary = "Salvar estoquista", description = "Endpoint para salvar um estoquista no sistema.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Salvar o estoquista.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EstoquistaResponseDTO.class))),
                    @ApiResponse(responseCode = "401", description = "Acesso não autorizado.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso proibido.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "409", description = "Conflito de dados.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Campos inválidos.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<EstoquistaResponseDTO> salvar(@Valid @RequestBody EstoquistaRequestDTO estoquistaRequestDTO) {
        Estoquista estoquistaSalvo = estoquistaService.salvar(estoquistaMapper.toEntity(estoquistaRequestDTO));
        URI url = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(estoquistaSalvo.getId())
                .toUri();
        return ResponseEntity.created(url).body(estoquistaMapper.toDTO(estoquistaSalvo));
    }

    @Operation(summary = "Buscar estoquista", description = "Endpoint para buscar um estoquista pelo ID no sistema.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sucesso na busca, retornando o estoquista.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EstoquistaResponseDTO.class))),
                    @ApiResponse(responseCode = "401", description = "Acesso não autorizado.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso proibido.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Estoquista não encontrado.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
            })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<EstoquistaResponseDTO> buscarPorId(@PathVariable Long id) {
        Estoquista estoquista = estoquistaService.buscar(id).get();
        return ResponseEntity.ok(estoquistaMapper.toDTO(estoquista));
    }

    @Operation(summary = "Deletar estoquista", description = "Endpoint para deletar um estoquista pelo ID no sistema.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Exclusão realizada.",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "401", description = "Acesso não autorizado.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso proibido.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Estoquista não encontrado.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
            })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id) {
        estoquistaService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }

}
