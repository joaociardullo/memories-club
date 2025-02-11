package com.devjoao.passagem.validatorStrategy.cadastrarStrategy;

import com.devjoao.passagem.dto.PassagemRequestDTO;
import com.devjoao.passagem.exceptions.CepInvalidExcpetion;

public class CepValidation implements ValidationStrategy {
    @Override
    public void validar(PassagemRequestDTO requestDTO) {
        if (requestDTO.getCep() == null || requestDTO.getCep().length() != 8) {
            throw new CepInvalidExcpetion("CEP inválido! Deve conter exatamente 8 dígitos.");
        }

    }
}
