package com.gianmarques.estoqueapi.controller;

import com.gianmarques.estoqueapi.dto.produto.*;
import com.gianmarques.estoqueapi.entity.Produto;
import com.gianmarques.estoqueapi.exception.model.ErrorMessage;
import com.gianmarques.estoqueapi.mapper.ProdutoMapper;
import com.gianmarques.estoqueapi.repository.pageable.GenericPageable;
import com.gianmarques.estoqueapi.repository.projection.ProdutoPorFornecedorProjection;
import com.gianmarques.estoqueapi.repository.projection.ProdutoProjection;
import com.gianmarques.estoqueapi.security.jwt.JwtUserDetails;
import com.gianmarques.estoqueapi.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Produtos", description = "Controller para gerenciar produtos.")
public class ProdutoController {

    private final ProdutoService produtoService;
    private final ProdutoMapper produtoMapper;

    public ProdutoController(ProdutoService produtoService, ProdutoMapper produtoMapper) {
        this.produtoService = produtoService;
        this.produtoMapper = produtoMapper;
    }

    @Operation(summary = "Listar produtos", description = "Endpoint para listar todos os produtos no sistema. (Apenas para estoquistas)",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de todos os produtos.",
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
    public ResponseEntity<GenericPageable> listarProdutos(@PageableDefault(size = 5, direction = Sort.Direction.ASC) Pageable pageable, @RequestParam(required = false) Boolean emFalta) {
        Page<ProdutoPorFornecedorProjection> produtoProjections = produtoService.listar(pageable, emFalta);
        return ResponseEntity.ok(produtoMapper.toPageableProdutosDTO(produtoProjections));
    }


    @Operation(summary = "Listar produtos", description = "Endpoint para listar os produtos criados pelo fornecedor. (Apenas para fornecedores)",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista dos produtos.",
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
    @GetMapping("/fornecedor")
    public ResponseEntity<GenericPageable> listar(@PageableDefault(size = 5, direction = Sort.Direction.ASC) @AuthenticationPrincipal JwtUserDetails jwtUserDetails, Pageable pageable) {
        Page<ProdutoProjection> produtos = produtoService.listar(pageable, jwtUserDetails.getId());
        return ResponseEntity.ok(produtoMapper.toPageableProdutosPorFornecedorDTO(produtos));
    }

    @Operation(summary = "Salvar produto", description = "Endpoint para salvar um produto no sistema. (Apenas para fornecedores)",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Salvar um produto.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProdutoResponseDTO.class))),
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
    @PreAuthorize("hasRole('FORNECEDOR')")
    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> salvar(@AuthenticationPrincipal JwtUserDetails jwtUserDetails, @Valid @RequestBody ProdutoRequestDTO produtoRequestDto) {
        Produto produtoSalvo = produtoService.salvar(jwtUserDetails.getId(), produtoMapper.toEntity(produtoRequestDto));
        URI url = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(produtoSalvo.getId())
                .toUri();
        return ResponseEntity.created(url).body(produtoMapper.toDTO(produtoSalvo));
    }


    @Operation(summary = "Buscar produto", description = "Endpoint para buscar um produto pelo ID no sistema. (Apenas para fornecedores)",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sucesso na busca, retornando o produto.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProdutoDetailsResponseDTO.class))),
                    @ApiResponse(responseCode = "401", description = "Acesso não autorizado.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso proibido.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Produto não encontrado.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PreAuthorize("hasRole('FORNECEDOR')")
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDetailsResponseDTO> buscarPorId(@AuthenticationPrincipal JwtUserDetails jwtUserDetails, @PathVariable Long id) {
        Long idFornecedor = jwtUserDetails.getId();
        Produto produto = produtoService.buscarPorId(id, idFornecedor);
        return ResponseEntity.ok(produtoMapper.toDetailsDTO(produto));
    }

    @Operation(summary = "Deletar produto", description = "Endpoint para deletar um produto pelo ID no sistema. (Apenas para fornecedores)",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Exclusão realizada..",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "401", description = "Acesso não autorizado.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso proibido.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Produto não encontrado.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PreAuthorize("hasRole('FORNECEDOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@AuthenticationPrincipal JwtUserDetails jwtUserDetails, @PathVariable Long id) {
        Long idFornecedor = jwtUserDetails.getId();
        produtoService.deletarPorId(id, idFornecedor);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualizar produto", description = "Endpoint para atualizar um produto pelo ID no sistema. (Apenas para fornecedores)",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Atualização realizada, retornando o produto.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProdutoUpdateResponseDTO.class))),
                    @ApiResponse(responseCode = "401", description = "Acesso não autorizado.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso proibido.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Produto não encontrado.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Campos inválidos.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            })

    @PreAuthorize("hasRole('FORNECEDOR')")
    @PatchMapping("/{id}")
    public ResponseEntity<ProdutoUpdateResponseDTO> atualizarPorId(@PathVariable Long id, @AuthenticationPrincipal JwtUserDetails jwtUserDetails, @Valid @RequestBody ProdutoUpdateRequestDTO produtoRequestDto) {
        Long idFornecedor = jwtUserDetails.getId();
        Produto produtoAtualizado = produtoService.editar(id, idFornecedor, produtoMapper.toEntity(produtoRequestDto));
        return ResponseEntity.ok().body(produtoMapper.toUpdateDTO(produtoAtualizado));
    }

}
