package com.devjoao.passagem.validatorStrategy.updateStrategy;

import com.devjoao.passagem.dto.PassagemRequestDTO;
import com.devjoao.passagem.exceptions.IdInvalidException;

import java.util.Objects;

public class PassagemRequestDTOValidator implements ValidatorStrategy<PassagemRequestDTO> {
    private static final String PASSAGEM_NAO_CADASTRADOS = "Passagem não cadastrada.";

    @Override
    public void validate(PassagemRequestDTO dto) {
        if (Objects.isNull(dto)) {
            throw new IdInvalidException(PASSAGEM_NAO_CADASTRADOS);
        }
    }
}
