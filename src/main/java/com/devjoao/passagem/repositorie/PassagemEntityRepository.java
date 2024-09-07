package com.devjoao.passagem.repositorie;

import com.devjoao.passagem.entity.PassagemEntity;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassagemEntityRepository extends JpaRepository<PassagemEntity, Long> {

    @Query(value = "SELECT id, nome_cliente, sobrenome, dia_viagem, pais, cidade, forma_pagamento, quantidade_integrantes, companhia_area, cpf, celular, cep, logradouro, bairro, localidade, uf, estado, regiao\n" +
            "FROM public.cadastro_passagem\n" +
            "where cpf = cpf\n" +
            "order by id desc ;",nativeQuery = true)
    List<PassagemEntity> findByCpf(@Param("cpf") String cpf);
}