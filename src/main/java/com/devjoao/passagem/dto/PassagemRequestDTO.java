package com.devjoao.passagem.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PassagemRequestDTO {

    private String nomeCliente;

    private String sobrenome;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private String diaViagem;

    private String pais;

    private String estado;

    private String cidade;

    private String formaPagamento;

    private Integer qtdIntegrantes;

    private String companhiaArea;

    private String cpf;

    private String celular;

    private String cep;

    private String email;
}
