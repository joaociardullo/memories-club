package com.devjoao.passagem.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class PassagemResponseDTO {

    private HttpStatus code;
    private String message;
    private Object content;

}
