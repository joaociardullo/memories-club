package com.devjoao.passagem.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class PassagemResponseDTO {

    private HttpStatus code;
    private String message;
    private Object content;

}
