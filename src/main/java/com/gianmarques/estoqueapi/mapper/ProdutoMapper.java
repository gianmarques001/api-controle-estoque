package com.gianmarques.estoqueapi.mapper;

import com.gianmarques.estoqueapi.dto.produto.*;
import com.gianmarques.estoqueapi.entity.Produto;
import com.gianmarques.estoqueapi.repository.pageable.GenericPageable;
import com.gianmarques.estoqueapi.repository.projection.ProdutoPorFornecedorProjection;
import com.gianmarques.estoqueapi.repository.projection.ProdutoProjection;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    Produto toEntity(ProdutoRequestDTO requestDTO);

    Produto toEntity(ProdutoUpdateRequestDTO requestDTO);

    ProdutoResponseDTO toDTO(Produto produto);

    ProdutoDetailsResponseDTO toDetailsDTO(Produto produto);

    ProdutoUpdateResponseDTO toUpdateDTO(Produto produto);

    GenericPageable<ProdutoResponseDTO> toPageableProdutosPorFornecedorDTO(Page<ProdutoProjection> page);

    GenericPageable<ProdutoPageableResponseDTO> toPageableProdutosDTO(Page<ProdutoPorFornecedorProjection> page);


}
