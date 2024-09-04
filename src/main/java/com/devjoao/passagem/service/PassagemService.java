package com.devjoao.passagem.service;

import com.devjoao.passagem.dto.PassagemRequestDTO;
import com.devjoao.passagem.dto.PassagemResponseDTO;

public interface PassagemService {
    PassagemResponseDTO cadastroPassagemCliente(PassagemRequestDTO requestDTO);
}
