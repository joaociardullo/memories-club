package com.devjoao.passagem.validatorStrategy;

import com.devjoao.passagem.exceptions.IdInvalidException;

public class NotNullValidator implements IdValidator.Validator {
    @Override
    public void validate(String id) throws IdInvalidException {
        if (id == null) {
            throw new IdInvalidException("id não existe ou o campo nao foi informado");
        }
    }
}
