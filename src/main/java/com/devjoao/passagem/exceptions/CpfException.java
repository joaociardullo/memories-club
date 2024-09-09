package com.devjoao.passagem.exceptions;

import java.lang.RuntimeException;

public class CpfException extends RuntimeException {
    public CpfException(String s) {
        super(s);
    }
}
