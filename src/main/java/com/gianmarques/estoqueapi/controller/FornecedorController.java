package com.gianmarques.estoqueapi.controller;

import com.gianmarques.estoqueapi.dto.fornecedor.FornecedorDetailsResponseDTO;
import com.gianmarques.estoqueapi.dto.fornecedor.FornecedorRequestDTO;
import com.gianmarques.estoqueapi.dto.fornecedor.FornecedorResponseDTO;
import com.gianmarques.estoqueapi.dto.fornecedor.FornecedorUpdateRequestDTO;
import com.gianmarques.estoqueapi.entity.Fornecedor;
import com.gianmarques.estoqueapi.exception.model.ErrorMessage;
import com.gianmarques.estoqueapi.mapper.FornecedorMapper;
import com.gianmarques.estoqueapi.service.FornecedorService;
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
@RequestMapping("/api/v1/fornecedores")
@Tag(name = "Fornecedores", description = "Controller para gerenciar fornecedores.")
public class FornecedorController {

    private final FornecedorService fornecedorService;
    private final FornecedorMapper fornecedorMapper;

    public FornecedorController(FornecedorService fornecedorService, FornecedorMapper fornecedorMapper) {
        this.fornecedorService = fornecedorService;
        this.fornecedorMapper = fornecedorMapper;
    }

    @Operation(summary = "Listar fornecedores", description = "Endpoint para listar fornecedores no sistema. (Apenas para admins)",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista dos fornecedores.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = FornecedorResponseDTO.class))),
                    @ApiResponse(responseCode = "401", description = "Acesso não autorizado.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso proibido.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
            })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<FornecedorResponseDTO>> listar() {
        List<FornecedorResponseDTO> fornecedores = fornecedorService.listar().stream().map(fornecedorMapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(fornecedores);
    }

    @Operation(summary = "Salvar fornecedor", description = "Endpoint para salvar um fornecedor no sistema. (Apenas para admins)",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Salvar o fornecedor.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = FornecedorResponseDTO.class))),
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
    public ResponseEntity<FornecedorResponseDTO> salvar(@Valid @RequestBody FornecedorRequestDTO fornecedorRequestDTO) {
        Fornecedor fornecedorSalvo = fornecedorService.salvar(fornecedorMapper.toEntity(fornecedorRequestDTO));
        URI url = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(fornecedorSalvo.getId())
                .toUri();
        return ResponseEntity.created(url).body(fornecedorMapper.toDTO(fornecedorSalvo));
    }

    @Operation(summary = "Buscar fornecedor", description = "Endpoint para buscar um fornecedor pelo ID no sistema. (Para admins e estoquistas)",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sucesso na busca, retornando o estoquista.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = FornecedorDetailsResponseDTO.class))),
                    @ApiResponse(responseCode = "401", description = "Acesso não autorizado.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso proibido.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
            })
    @PreAuthorize("hasAnyRole('ADMIN', 'ESTOQUISTA')")
    @GetMapping("/{id}")
    public ResponseEntity<FornecedorDetailsResponseDTO> buscarPorId(@PathVariable Long id) {
        Fornecedor fornecedor = fornecedorService.buscar(id).get();
        return ResponseEntity.ok(fornecedorMapper.toDetailsDTO(fornecedor));
    }

    @Operation(summary = "Deletar fornecedor", description = "Endpoint para deletar um fornecedor pelo ID no sistema. (Apenas para admins)",
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
                    @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
            })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id) {
        fornecedorService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualizar fornecedor", description = "Endpoint para atualizar um fornecedor pelo ID no sistema. (Apenas para admins)",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Atualização realizada, retornando o fornecedor.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = FornecedorDetailsResponseDTO.class))),
                    @ApiResponse(responseCode = "401", description = "Acesso não autorizado.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso proibido.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Campos inválidos.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<FornecedorDetailsResponseDTO> atualizarPorId(@PathVariable Long id, @Valid @RequestBody FornecedorUpdateRequestDTO fornecedorUpdateRequestDTO) {
        Fornecedor fornecedorAtualizado = fornecedorService.editarPorId(id, fornecedorMapper.toEntity(fornecedorUpdateRequestDTO));
        return ResponseEntity.ok().body(fornecedorMapper.toDetailsDTO(fornecedorAtualizado));
    }

}
