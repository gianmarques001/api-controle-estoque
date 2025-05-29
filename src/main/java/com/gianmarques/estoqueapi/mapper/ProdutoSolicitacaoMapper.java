package com.gianmarques.estoqueapi.mapper;

import com.gianmarques.estoqueapi.entity.Produto;
import com.gianmarques.estoqueapi.service.ProdutoService;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
public class ProdutoSolicitacaoMapper {

    private final ProdutoService produtoService;

    public ProdutoSolicitacaoMapper(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @Named("mapToProduto")
    public Produto mapToProduto(Long idProduto) {
        return produtoService.buscarPorId(idProduto);
    }

}
