package com.devjoao.passagem.validatorStrategy.cadastrarStrategy;

import com.devjoao.passagem.dto.PassagemRequestDTO;

public interface ValidationStrategy  {
    void validar(PassagemRequestDTO requestDTO);
}
