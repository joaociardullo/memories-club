package com.devjoao.passagem.validatorStrategy;

import com.devjoao.passagem.exceptions.IdInvalidException;

public class IdValidator {
    public interface Validator {
        void validate(String id) throws IdInvalidException;
    }
}
