package com.gianmarques.estoqueapi.mapper;

import com.gianmarques.estoqueapi.dto.solicitacao.SolicitacaoFornecedorPageableResponseDTO;
import com.gianmarques.estoqueapi.dto.solicitacao.SolicitacaoPageableResponseDTO;
import com.gianmarques.estoqueapi.dto.solicitacao.SolicitacaoRequestDTO;
import com.gianmarques.estoqueapi.entity.Solicitacao;
import com.gianmarques.estoqueapi.repository.pageable.GenericPageable;
import com.gianmarques.estoqueapi.repository.projection.SolicitacaoPorFornecedorProjection;
import com.gianmarques.estoqueapi.repository.projection.SolicitacaoProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", uses = {ProdutoSolicitacaoMapper.class})
public abstract class SolicitacaoMapper {

    @Mapping(target = "quantidadeSolicitada", source = "qtdSolicitada")
    @Mapping(target = "produto", source = "idProduto", qualifiedByName = "mapToProduto")
    public abstract Solicitacao toEntity(SolicitacaoRequestDTO solicitacaoRequestDTO);

    public abstract GenericPageable<SolicitacaoFornecedorPageableResponseDTO> toPageableFornecedor(Page<SolicitacaoPorFornecedorProjection> page);

    public abstract GenericPageable<SolicitacaoPageableResponseDTO> toPageableDTO(Page<SolicitacaoProjection> page);

}
