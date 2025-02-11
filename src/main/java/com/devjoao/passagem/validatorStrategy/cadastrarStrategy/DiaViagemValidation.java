package com.devjoao.passagem.validatorStrategy.cadastrarStrategy;

import com.devjoao.passagem.dto.PassagemRequestDTO;
import com.devjoao.passagem.exceptions.InvalidPropertiesFormatException;

public class DiaViagemValidation implements ValidationStrategy {
    @Override
    public void validar(PassagemRequestDTO requestDTO) {
        if (requestDTO.getDiaViagem() == null) {
            throw new InvalidPropertiesFormatException("É obrigatório informar a data da viagem.");
        }
    }
}
