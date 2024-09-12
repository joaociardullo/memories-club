package com.devjoao.passagem.validatorStrategy;

import com.devjoao.passagem.dto.PassagemRequestDTO;
import com.devjoao.passagem.exceptions.InvalidPropertiesFormatException;

public class NomeClienteValidator implements ValidationStrategy.Validator {
    @Override
    public void validate(PassagemRequestDTO requestDTO) throws InvalidPropertiesFormatException {
        if (requestDTO.getNomeCliente().isBlank()) {
            throw new InvalidPropertiesFormatException("Obrigatorio passar o nome do cliente.");
        }
    }
}
