package com.devjoao.passagem.utils;

import com.devjoao.passagem.dto.EnderecoDTO;
import com.devjoao.passagem.dto.PassagemResponseDTO;
import com.devjoao.passagem.entity.EnderecoEntity;
import com.devjoao.passagem.entity.PassagemEntity;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Getter
public class ReponseUtils {

    private ReponseUtils() {
    }

    public static PassagemResponseDTO toResponseDTOUpdate(PassagemEntity passagemEntity) {
        return PassagemResponseDTO.builder()
                .code(HttpStatus.CREATED)
                .message("Dados atualizado com sucesso:")
                .content(passagemEntity).build();
    }

    public static PassagemResponseDTO getPassagemResponseDTO(Optional<PassagemEntity> result) {
        return PassagemResponseDTO.builder()
                .code(HttpStatus.CREATED)
                .message("Dados cadastrado com sucesso:")
                .content(result).build();
    }

    public static PassagemResponseDTO toResponseBuscarAll(List<PassagemEntity> listCadastro) {
        return PassagemResponseDTO.builder()
                .code(HttpStatus.CREATED)
                .message("Dados cadastrado com sucesso:")
                .content(listCadastro).build();
    }

    public static EnderecoDTO getEnderecoCepDTO(EnderecoEntity endereco) {
        return EnderecoDTO.builder()
                .cep(endereco.getCep())
                .bairro(endereco.getBairro())
                .uf(endereco.getUf())
                .regiao(endereco.getRegiao())
                .estado(endereco.getEstado())
                .localidade(endereco.getLocalidade())
                .logradouro(endereco.getLogradouro())
                .bairro(endereco.getBairro())
                .dtCadastroEndereco(LocalDate.now()).build();
    }

}
