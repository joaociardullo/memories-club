package com.devjoao.passagem.validatorStrategy.cadastrarStrategy;

import com.devjoao.passagem.dto.PassagemRequestDTO;
import com.devjoao.passagem.repositorie.PassagemEntityRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Log4j2
@Configuration
public class ValidatorContext {
    private final List<ValidationStrategy> strategies;

    public ValidatorContext(PassagemEntityRepository repository) {
        this.strategies = List.of(
                new CepValidation(),
                new EmailValidation(),
                new NomeClienteValidation(),
                new DiaViagemValidation(),
                new CpfValidation(repository)
        );
    }

    public void validate(PassagemRequestDTO requestDTO) {
        for (ValidationStrategy strategy : strategies) {
            log.info("Validar regras para cadastramento ; [{}] ", strategies);
            strategy.validar(requestDTO);
        }
    }
}
