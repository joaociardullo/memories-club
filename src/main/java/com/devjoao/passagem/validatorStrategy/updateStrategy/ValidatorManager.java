package com.devjoao.passagem.validatorStrategy.updateStrategy;

import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ValidatorManager {
    private final List<ValidatorStrategy<?>> validators = new ArrayList<>();

    public <T> void addValidator(ValidatorStrategy<T> validator) {
        validators.add(validator);
    }

    @SuppressWarnings("unchecked")
    public <T> void validate(T value) {
        for (ValidatorStrategy<?> validator : validators) {
            if (validator != null) {
                ((ValidatorStrategy<T>) validator).validate(value);
            }
        }
    }
}
