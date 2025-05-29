package com.gianmarques.estoqueapi.controller;


import com.gianmarques.estoqueapi.dto.produto.ProdutoUpdateRequestDTO;
import com.gianmarques.estoqueapi.dto.solicitacao.SolicitacaoRequestDTO;
import com.gianmarques.estoqueapi.entity.Solicitacao;
import com.gianmarques.estoqueapi.exception.model.ErrorMessage;
import com.gianmarques.estoqueapi.mapper.ProdutoMapper;
import com.gianmarques.estoqueapi.mapper.SolicitacaoMapper;
import com.gianmarques.estoqueapi.repository.pageable.GenericPageable;
import com.gianmarques.estoqueapi.repository.projection.SolicitacaoPorFornecedorProjection;
import com.gianmarques.estoqueapi.repository.projection.SolicitacaoProjection;
import com.gianmarques.estoqueapi.security.jwt.JwtUserDetails;
import com.gianmarques.estoqueapi.service.SolicitacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/solicitacoes")
@Tag(name = "Solicitacoes", description = "Controller para gerenciar solicitacoes.")
public class SolicitacaoController {

    private final SolicitacaoService solicitacaoService;
    private final SolicitacaoMapper solicitacaoMapper;

    private final ProdutoMapper produtoMapper;

    public SolicitacaoController(SolicitacaoService solicitacaoService, SolicitacaoMapper solicitacaoMapper, ProdutoMapper produtoMapper) {
        this.solicitacaoService = solicitacaoService;
        this.solicitacaoMapper = solicitacaoMapper;
        this.produtoMapper = produtoMapper;
    }

    @Operation(summary = "Salvar solicitação", description = "Endpoint para salvar uma solicitação no sistema. (Apenas para estoquistas)",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Salvar solicitação.",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "401", description = "Acesso não autorizado.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso proibido.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Campos inválidos.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PreAuthorize("hasRole('ESTOQUISTA')")
    @PostMapping
    public ResponseEntity<Void> salvar(@AuthenticationPrincipal JwtUserDetails jwtUserDetails, @Valid @RequestBody SolicitacaoRequestDTO solicitacaoRequestDTO) {
        Solicitacao solicitacaoSalva = solicitacaoService.salvar(jwtUserDetails.getId(), solicitacaoMapper.toEntity(solicitacaoRequestDTO));

        URI url = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(solicitacaoSalva.getId())
                .toUri();
        return ResponseEntity.created(url).build();
    }


    @Operation(summary = "Listar solicitações", description = "Endpoint para listar solicitações no sistema. (Apenas para estoquistas)",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista das solicitações.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = GenericPageable.class))),
                    @ApiResponse(responseCode = "401", description = "Acesso não autorizado.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso proibido.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
            })
    @PreAuthorize("hasRole('ESTOQUISTA')")
    @GetMapping
    public ResponseEntity<GenericPageable> listar(Pageable pageable) {
        Page<SolicitacaoProjection> solicitacoes = solicitacaoService.listar(pageable);
        return ResponseEntity.ok(solicitacaoMapper.toPageableDTO(solicitacoes));
    }

    @Operation(summary = "Listar solicitações por fornecedor ", description = "Endpoint para listar solicitações por fornecedor no sistema. (Apenas para fornecedores)",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista das solicitações pelo fornecedor.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = GenericPageable.class))),
                    @ApiResponse(responseCode = "401", description = "Acesso não autorizado.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso proibido.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
            })
    @PreAuthorize("hasRole('FORNECEDOR')")
    @GetMapping("/fornecedores")
    public ResponseEntity<GenericPageable> listar(@AuthenticationPrincipal JwtUserDetails jwtUserDetails, Pageable pageable) {
        Page<SolicitacaoPorFornecedorProjection> solicitacoesEmAberto = solicitacaoService.listar(pageable, jwtUserDetails.getId());
        return ResponseEntity.ok(solicitacaoMapper.toPageableFornecedor(solicitacoesEmAberto));
    }

    @Operation(summary = "Atualizar solicitação", description = "Endpoint para atualizar solicitação pelo ID no sistema. (Apenas para fornecedores)",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Solicitação atualizada.",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "401", description = "Acesso não autorizado.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso proibido.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Solicitação não encontrada.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
            })
    @PreAuthorize("hasRole('FORNECEDOR')")
    @PatchMapping("/fornecedores/{id}")
    public ResponseEntity<Void> atualizarSolicitacao(@AuthenticationPrincipal JwtUserDetails jwtUserDetails, @PathVariable Long id, @Valid @RequestBody ProdutoUpdateRequestDTO produtoUpdateRequestDTO) {
        solicitacaoService.editar(id, jwtUserDetails.getId(), produtoMapper.toEntity(produtoUpdateRequestDTO));
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Concluir solicitação", description = "Endpoint para dar baixa nas solicitações no sistema. (Apenas para estoquistas)",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resultado das baixas (concluídas e pendentes).",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "401", description = "Acesso não autorizado.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso proibido.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
            })
    @PreAuthorize("hasRole('ESTOQUISTA')")
    @PatchMapping("/baixa")
    public ResponseEntity<?> concluirSolicitacao() {
        Map<String, Integer> stringIntegerMap = solicitacaoService.concluirSolicitacao();
        return ResponseEntity.ok(stringIntegerMap);
    }

}
