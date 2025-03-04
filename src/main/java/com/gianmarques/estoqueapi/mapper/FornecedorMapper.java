package com.gianmarques.estoqueapi.mapper;


import com.gianmarques.estoqueapi.dto.fornecedor.FornecedorDetailsResponseDTO;
import com.gianmarques.estoqueapi.dto.fornecedor.FornecedorRequestDTO;
import com.gianmarques.estoqueapi.dto.fornecedor.FornecedorResponseDTO;
import com.gianmarques.estoqueapi.entity.Fornecedor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FornecedorMapper {

    Fornecedor toEntity(FornecedorRequestDTO requestDTO);

    FornecedorResponseDTO toDTO(Fornecedor fornecedor);

    FornecedorDetailsResponseDTO toDetailsDTO(Fornecedor fornecedor);
}
