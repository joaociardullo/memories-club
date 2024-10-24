package com.devjoao.passagem.validatorStrategy;

import com.devjoao.passagem.dto.PassagemRequestDTO;
import com.devjoao.passagem.exceptions.CpfException;
import com.devjoao.passagem.exceptions.CpfNullException;
import com.devjoao.passagem.exceptions.InvalidPropertiesFormatException;
import com.devjoao.passagem.repositorie.PassagemEntityRepository;

public class ValidationStrategyCadastrar implements ValidacaoStrategy {
    @Override
    public void validar(PassagemRequestDTO requestDTO) {
        if (requestDTO.getCep().length() != 11 && requestDTO.getCep() == null) {
            throw new NumberFormatException("Cep nao segue o padrão !");
        }
    }

    public static class CpfValidationStrategy implements ValidacaoStrategy {
        @Override
        public void validar(PassagemRequestDTO requestDTO) throws InvalidPropertiesFormatException {
            if (requestDTO.getCpf().isEmpty()) {
                throw new CpfNullException("Inserir o CPF");
            }
            if (requestDTO.getCpf().length() != 11) {
                throw new CpfNullException("Tamanho de cpf invalido: Digite corretamente");
            }
        }
    }

    public static class DiaViagemValidationStrategy implements ValidacaoStrategy {
        @Override
        public void validar(PassagemRequestDTO requestDTO) throws InvalidPropertiesFormatException {
            if (requestDTO.getDiaViagem() == null) {
                throw new InvalidPropertiesFormatException("Obrigatorio passar a data da viagem.");
            }
        }
    }

    public static class EmailValidationStrategy implements ValidacaoStrategy {
        @Override
        public void validar(PassagemRequestDTO requestDTO) throws InvalidPropertiesFormatException {
            if (requestDTO.getEmail() == null || requestDTO.getEmail().isEmpty() || !requestDTO.getEmail().contains("@")) {
                throw new InvalidPropertiesFormatException("Obrigatorio ter '@' no email.");
            }
        }
    }

    public static class NomeClienteValidationStrategy implements ValidacaoStrategy {
        @Override
        public void validar(PassagemRequestDTO requestDTO) throws InvalidPropertiesFormatException {
            if (requestDTO.getNomeCliente().isBlank()) {
                throw new InvalidPropertiesFormatException("Obrigatorio passar o nome do cliente.");
            }
        }
    }

    public static class CpfExistenteValidationStrategy implements ValidacaoStrategy {
        private final PassagemEntityRepository repository;

        public CpfExistenteValidationStrategy(PassagemEntityRepository repository) {
            this.repository = repository;
        }

        @Override
        public void validar(PassagemRequestDTO requestDTO) throws InvalidPropertiesFormatException {
            var buscarcpf = repository.findByCpf(requestDTO.getCpf());
            if (!buscarcpf.isEmpty() && buscarcpf.get(0).getCpf().equals(requestDTO.getCpf())) {
                throw new CpfException("CPF já cadastrado");
            }
        }
    }
}
