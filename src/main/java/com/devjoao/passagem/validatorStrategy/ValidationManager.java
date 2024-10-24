package com.devjoao.passagem.validatorStrategy;

import com.devjoao.passagem.dto.PassagemRequestDTO;
import com.devjoao.passagem.exceptions.InvalidPropertiesFormatException;

import java.util.List;

public class ValidationManager {
    private final List<ValidacaoStrategy> strategies;

    public ValidationManager(List<ValidacaoStrategy> strategies) {
        this.strategies = strategies;
    }

    public void validate(PassagemRequestDTO requestDTO) throws InvalidPropertiesFormatException {
        for (ValidacaoStrategy strategy : strategies) {
            strategy.validar(requestDTO);
        }
    }
}
