package com.devjoao.passagem.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class EnderecoDTO {

    private Long cdEndereco;
    private String cep;
    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;
    private String estado;
    private String regiao;

    @JsonFormat(pattern = "dd-MM-yyyy:")
    private LocalDate dtCadastroEndereco;
}
