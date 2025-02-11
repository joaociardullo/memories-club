package com.devjoao.passagem.validatorStrategy.cadastrarStrategy;

import com.devjoao.passagem.dto.PassagemRequestDTO;
import com.devjoao.passagem.exceptions.InvalidPropertiesFormatException;

public class EmailValidation implements ValidationStrategy {
    @Override
    public void validar(PassagemRequestDTO requestDTO) {
        if (requestDTO.getEmail() == null || requestDTO.getEmail().isBlank()) {
            throw new InvalidPropertiesFormatException("O e-mail é obrigatório.");
        }
        if (!requestDTO.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw new InvalidPropertiesFormatException("E-mail inválido! Deve seguir o formato exemplo@dominio.com.");
        }
    }
}
