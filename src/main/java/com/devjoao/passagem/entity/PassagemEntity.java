package com.devjoao.passagem.entity;

import com.devjoao.passagem.dto.EnderecoCepDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "cadastro_passagem")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class PassagemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeCliente;

    private String sobrenome;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "dia_viagem")
    private String diaViagem;

    private String pais;

    @Column(name = "Estado", updatable = false, insertable = false, length = 50)
    private String estado;

    private String cidade;

    private String formaPagamento;

    @Column(name = "quantidade_integrantes")
    private Integer qtdIntegrantes;

    @Column(name = "companhia_area")
    private String companhiaArea;

    @Column(name = "CPF")
    private String cpf;

    @Column(name = "celular")
    private String celular;

    private EnderecoCepDTO cep;

}