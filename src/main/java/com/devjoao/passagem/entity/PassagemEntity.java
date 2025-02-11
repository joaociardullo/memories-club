package com.devjoao.passagem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "cadastro_passagem")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class PassagemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    private String nomeCliente;
    private String sobrenome;
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "dia_viagem")
    private String diaViagem;
    private String pais;
    @Column(name = "Estado", updatable = false, length = 50)
    private String estado;

    private String cidade;
    private String formaPagamento;

    @Column(name = "quantidade_integrantes")
    private Integer qtdIntegrantes;

    @Column(name = "companhia_area")
    private String companhiaArea;

    @Column(name = "CPF", length = 11)
    private String cpf;

    @Column(name = "celular")
    private String celular;

    @Column(name = "email", length = 50)
    private String email;
    @JsonFormat(pattern = "dd-MM-yyy")
    private LocalDate dtCadastro;

    @ManyToOne
    @JoinColumn(name = "endereco_id", referencedColumnName = "cdEndereco")
    private EnderecoEntity endereco;

    @JsonFormat(pattern = "dd-MM-yyy")
    private LocalDate dtCadastroAtualizado;

}