package com.devjoao.passagem.validatorStrategy;

import com.devjoao.passagem.exceptions.IdInvalidException;

public class NoWhitespaceValidator implements IdValidator.Validator {
    @Override
    public void validate(String id) throws IdInvalidException {
        if (id.contains(" ")) {
            throw new IdInvalidException("id não existe ou o campo nao foi informado");
        }
    }
}
