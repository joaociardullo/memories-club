package com.devjoao.passagem.mappper;

import com.devjoao.passagem.dto.EnderecoDTO;
import com.devjoao.passagem.dto.PassagemRequestDTO;
import com.devjoao.passagem.dto.PassagemResponseDTO;
import com.devjoao.passagem.entity.EnderecoEntity;
import com.devjoao.passagem.entity.PassagemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PassagemMapper {

    PassagemEntity toPassagem(PassagemRequestDTO passagemRequestDTO);

    @Mapping(target = "cep", source = "cep")
    EnderecoEntity toEndereco(EnderecoDTO endereco);

    @Mapping(source = "dtCadastro", target = "dtCadastro")
    PassagemEntity toAtualizar(PassagemRequestDTO passagem);

    //Cria um payload com passagem e cep
    default PassagemResponseDTO toResponse(PassagemEntity passagem) {
        Map<String, Object> content = new HashMap<>();
        content.put("passagem", passagem);

        return PassagemResponseDTO.builder()
                .code(HttpStatus.CREATED)
                .message("Cadastro realizado com sucesso!")
                .content(content)
                .build();
    }

}
