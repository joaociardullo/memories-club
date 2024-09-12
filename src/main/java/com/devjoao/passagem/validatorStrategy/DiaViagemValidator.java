package com.devjoao.passagem.validatorStrategy;

import com.devjoao.passagem.dto.PassagemRequestDTO;
import com.devjoao.passagem.exceptions.InvalidPropertiesFormatException;

public class DiaViagemValidator implements ValidationStrategy.Validator {
    @Override
    public void validate(PassagemRequestDTO requestDTO) throws InvalidPropertiesFormatException {
        if (requestDTO.getDiaViagem() == null) {
            throw new InvalidPropertiesFormatException("Obrigatorio passar a data da viagem.");
        }
    }
}
