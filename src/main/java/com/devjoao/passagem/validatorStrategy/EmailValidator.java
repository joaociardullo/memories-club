package com.devjoao.passagem.validatorStrategy;

import com.devjoao.passagem.dto.PassagemRequestDTO;
import com.devjoao.passagem.exceptions.InvalidPropertiesFormatException;

public class EmailValidator implements ValidationStrategy.Validator {
    @Override
    public void validate(PassagemRequestDTO requestDTO) throws InvalidPropertiesFormatException {
        if (requestDTO.getEmail() == null || requestDTO.getEmail().isEmpty() || !requestDTO.getEmail().contains("@")) {
            throw new InvalidPropertiesFormatException("Obrigatorio ter '@' no email.");
        }    }
}
