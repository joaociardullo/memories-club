package com.devjoao.passagem.service;

import com.devjoao.passagem.dto.PassagemRequestDTO;
import com.devjoao.passagem.dto.PassagemResponseDTO;

import java.util.InvalidPropertiesFormatException;

public interface PassagemService {
    PassagemResponseDTO cadastroPassagemCliente(PassagemRequestDTO requestDTO) throws InvalidPropertiesFormatException;

    PassagemResponseDTO buscarClienteCadastrado(String id);

    PassagemResponseDTO buscarTodosClientes();

    PassagemResponseDTO atualizarCadastroCliente(String id, PassagemRequestDTO requestDTO);

    PassagemResponseDTO deletar(Long id);
}
