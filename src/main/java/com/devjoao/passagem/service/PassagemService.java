package com.devjoao.passagem.service;

import com.devjoao.passagem.dto.PassagemRequestDTO;
import com.devjoao.passagem.dto.PassagemResponseDTO;

import java.util.InvalidPropertiesFormatException;

public interface PassagemService {
    PassagemResponseDTO cadastroPassagemCliente(PassagemRequestDTO requestDTO) throws InvalidPropertiesFormatException;
}