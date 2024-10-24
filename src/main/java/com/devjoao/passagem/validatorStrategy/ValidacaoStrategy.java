package com.devjoao.passagem.validatorStrategy;

import com.devjoao.passagem.dto.PassagemRequestDTO;

public interface ValidacaoStrategy {
    void validar(PassagemRequestDTO requestDTO);
}
