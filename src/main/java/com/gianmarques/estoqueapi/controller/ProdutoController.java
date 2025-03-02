package com.gianmarques.estoqueapi.controller;

import com.gianmarques.estoqueapi.dto.ProdutoRequestDto;
import com.gianmarques.estoqueapi.dto.ProdutoResponseDto;
import com.gianmarques.estoqueapi.entity.Produto;
import com.gianmarques.estoqueapi.entity.enums.ECategoriaProduto;
import com.gianmarques.estoqueapi.service.ProdutoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listar() {
        return ResponseEntity.ok(produtoService.listar());
    }

    @PostMapping
    public ResponseEntity<ProdutoResponseDto> salvar(@RequestBody ProdutoRequestDto produtoRequestDto) {

        Produto produto = new Produto();
        produto.setNome(produtoRequestDto.nome());
        produto.setDescricao(produtoRequestDto.descricao());
        produto.setQuantidade(produtoRequestDto.quantidade());
        produto.setPreco(produtoRequestDto.preco());
        produto.setFornecedor(null);
        produto.setCategoria(ECategoriaProduto.valueOf(produtoRequestDto.categoria()));
        Produto produtoSalvo = produtoService.salvar(produto);

        URI url = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(produtoSalvo.getId())
                .toUri();

        ProdutoResponseDto responseDto = new ProdutoResponseDto(produtoSalvo.getId(), produtoSalvo.getNome(), url);
        return ResponseEntity.created(url).body(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<Produto> produto = produtoService.buscarPorId(id);
        if (produto.isPresent()) {
            return ResponseEntity.ok(produto.get());
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id) {
        produtoService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Produto> atualizarPorId(@PathVariable Long id, @RequestBody Produto produtoRequestDto) {
        Produto produtoAtualizado = produtoService.editarPorId(id, produtoRequestDto);

        return ResponseEntity.ok().body(produtoAtualizado);
    }

}
