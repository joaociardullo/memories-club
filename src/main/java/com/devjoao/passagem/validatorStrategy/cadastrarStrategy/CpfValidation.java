package com.devjoao.passagem.validatorStrategy.cadastrarStrategy;

import com.devjoao.passagem.dto.PassagemRequestDTO;
import com.devjoao.passagem.exceptions.CpfNullException;
import com.devjoao.passagem.repositorie.PassagemEntityRepository;

public class CpfValidation implements ValidationStrategy {
    public CpfValidation(PassagemEntityRepository repository) {
    }

    @Override
    public void validar(PassagemRequestDTO requestDTO) {
        if (requestDTO.getCpf() == null || requestDTO.getCpf().isBlank())
            throw new CpfNullException("CPF é obrigatório.");


        if (!requestDTO.getCpf().matches("\\d{11}"))
            throw new CpfNullException("CPF inválido! Deve conter exatamente 11 números.");

    }
}
