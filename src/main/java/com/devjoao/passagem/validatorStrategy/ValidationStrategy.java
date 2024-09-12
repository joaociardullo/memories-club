package com.devjoao.passagem.validatorStrategy;

import com.devjoao.passagem.dto.PassagemRequestDTO;
import com.devjoao.passagem.exceptions.InvalidPropertiesFormatException;

public class ValidationStrategy {
    public interface Validator {
        void validate(PassagemRequestDTO requestDTO) throws InvalidPropertiesFormatException;
    }

}
