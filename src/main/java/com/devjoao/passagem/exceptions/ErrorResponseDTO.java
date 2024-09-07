package com.devjoao.passagem.exceptions;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseDTO {

    private String status;
    private String message;
    private String erro;
    private String path;
    private String timesStamp;
}
