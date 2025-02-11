package com.devjoao.passagem.validatorStrategy.updateStrategy;

public interface ValidatorStrategy<T> {
    void validate(T value);
}
