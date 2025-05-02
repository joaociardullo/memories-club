package com.devjoao.passagem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Table(name = "endereco")
@Data
@Entity
public class EnderecoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cdEndereco;
    private String cep;
    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;
    private String estado;
    private String regiao;
    @JsonFormat(pattern = "dd-MM-yyy")
    private LocalDate dtCadastroEndereco;
}
