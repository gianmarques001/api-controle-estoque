package com.gianmarques.estoqueapi.mapper;


import com.gianmarques.estoqueapi.dto.estoquista.EstoquistaRequestDTO;
import com.gianmarques.estoqueapi.dto.estoquista.EstoquistaResponseDTO;
import com.gianmarques.estoqueapi.entity.Estoquista;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EstoquistaMapper {

    Estoquista toEntity(EstoquistaRequestDTO estoquistaRequestDTO);

    EstoquistaResponseDTO toDTO(Estoquista estoquista);
}
