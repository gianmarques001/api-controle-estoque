package com.gianmarques.estoqueapi.mapper;


import com.gianmarques.estoqueapi.dto.fornecedor.FornecedorDetailsResponseDTO;
import com.gianmarques.estoqueapi.dto.fornecedor.FornecedorRequestDTO;
import com.gianmarques.estoqueapi.dto.fornecedor.FornecedorResponseDTO;
import com.gianmarques.estoqueapi.dto.fornecedor.FornecedorUpdateRequestDTO;
import com.gianmarques.estoqueapi.entity.Fornecedor;
import com.gianmarques.estoqueapi.entity.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FornecedorMapper {

    Fornecedor toEntity(FornecedorRequestDTO requestDTO);

    Fornecedor toEntity(FornecedorUpdateRequestDTO requestDTO);

    FornecedorResponseDTO toDTO(Fornecedor fornecedor);

    @Mapping(target = "produtos", source = "produtos", qualifiedByName = "productCount")
    FornecedorDetailsResponseDTO toDetailsDTO(Fornecedor fornecedor);

    @Named("productCount")
    default Integer productCount(List<Produto> produtos) {
        return produtos != null ? produtos.size() : 0;
    }
}
