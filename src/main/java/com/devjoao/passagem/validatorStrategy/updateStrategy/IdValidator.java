package com.devjoao.passagem.validatorStrategy.updateStrategy;

import com.devjoao.passagem.exceptions.IdInvalidException;

public class IdValidator implements ValidatorStrategy<String> {
    public static final String ID_NAO_EXISTE_OU_O_CAMPO_NAO_FOI_INFORMADO = "id não existe ou o campo nao foi informado";


    @Override
    public void validate(String id) {
        if (id == null || id.contains(" ")) {
            throw new IdInvalidException(ID_NAO_EXISTE_OU_O_CAMPO_NAO_FOI_INFORMADO);
        }
    }
}
