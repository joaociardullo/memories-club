package com.devjoao.passagem.validatorStrategy;

import com.devjoao.passagem.dto.PassagemRequestDTO;
import com.devjoao.passagem.exceptions.CpfNullException;
import com.devjoao.passagem.exceptions.InvalidPropertiesFormatException;

public class CpfValidator implements ValidationStrategy.Validator {
    @Override
    public void validate(PassagemRequestDTO requestDTO) throws InvalidPropertiesFormatException {
        if (requestDTO.getCpf().isEmpty()) {
            throw new CpfNullException("Inserir o CPF");
        }
        if (requestDTO.getCpf().length() != 11) {
            throw new CpfNullException("Tamanho de cpf invalido: Digite corretamente");
        }
    }
}
