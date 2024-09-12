package com.devjoao.passagem.validatorStrategy;

import com.devjoao.passagem.exceptions.IdInvalidException;

public class NotEmptyValidator implements IdValidator.Validator {
    @Override
    public void validate(String id) throws IdInvalidException {
        if (id.isEmpty()) {
            throw new IdInvalidException("id não existe ou o campo nao foi informado");
        }
    }
}