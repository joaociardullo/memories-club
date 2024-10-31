package com.devjoao.passagem.validatorStrategy;

import com.devjoao.passagem.dto.PassagemRequestDTO;
import com.devjoao.passagem.exceptions.InvalidPropertiesFormatException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ValidationManagerStratagy {
    private final List<ValidacaoStrategy> strategies;

    public ValidationManagerStratagy(List<ValidacaoStrategy> strategies) {
        this.strategies = strategies;
    }

    public void validate(PassagemRequestDTO requestDTO) throws InvalidPropertiesFormatException {
        for (ValidacaoStrategy strategy : strategies) {
            strategy.validar(requestDTO);
        }
    }
}
