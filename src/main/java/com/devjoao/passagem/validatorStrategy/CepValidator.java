package com.devjoao.passagem.validatorStrategy;

import com.devjoao.passagem.dto.PassagemRequestDTO;
import com.devjoao.passagem.exceptions.InvalidPropertiesFormatException;

public class CepValidator implements ValidationStrategy.Validator {
    @Override
    public void validate(PassagemRequestDTO requestDTO) throws InvalidPropertiesFormatException {
        if (requestDTO.getCep().length() != 11 && requestDTO.getCep() == null) {
            throw new NumberFormatException("Cep nao segue o padrão !");
        }
    }
}
