package com.devjoao.passagem.mappper;

import com.devjoao.passagem.dto.PassagemRequestDTO;
import com.devjoao.passagem.entity.PassagemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PassagemMapper {

    PassagemEntity toEntity(PassagemRequestDTO passagemRequestDTO);

}
