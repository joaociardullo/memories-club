package com.devjoao.passagem.mappper;

import com.devjoao.passagem.dto.EnderecoCepDTO;
import com.devjoao.passagem.dto.PassagemRequestDTO;
import com.devjoao.passagem.entity.PassagemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PassagemMapper {

    PassagemEntity toEntity(PassagemRequestDTO passagemRequestDTO);

    @Mapping(target = "cep", source = "cep")
    EnderecoCepDTO stringToEnderecoCepDTO(String cep);
}
