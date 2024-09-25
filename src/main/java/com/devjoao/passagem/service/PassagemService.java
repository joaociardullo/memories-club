package com.devjoao.passagem.service;

import com.devjoao.passagem.dto.PassagemRequestDTO;
import com.devjoao.passagem.dto.PassagemResponseDTO;
import com.devjoao.passagem.entity.PassagemEntity;

import java.util.InvalidPropertiesFormatException;

public interface PassagemService {
    PassagemResponseDTO cadastroPassagemCliente(PassagemRequestDTO requestDTO) throws InvalidPropertiesFormatException;

    PassagemResponseDTO bucarClienteCadastrado(String id);

    PassagemResponseDTO buscarTodosClientes();

    PassagemResponseDTO toResponseDTO(PassagemEntity passagemEntity);

    PassagemResponseDTO atualizarCadastroCliente(String id, PassagemRequestDTO requestDTO);
}
