package com.devjoao.passagem.validatorStrategy.cadastrarStrategy;

import com.devjoao.passagem.dto.PassagemRequestDTO;
import com.devjoao.passagem.exceptions.InvalidPropertiesFormatException;

public class NomeClienteValidation implements ValidationStrategy {
    @Override
    public void validar(PassagemRequestDTO requestDTO) {
        if (requestDTO.getNomeCliente() == null || requestDTO.getNomeCliente().isBlank()) {
            throw new InvalidPropertiesFormatException("O nome do cliente é obrigatório.");
        }
    }
}
