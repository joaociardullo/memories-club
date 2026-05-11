package com.devjoao.passagem.dto;

import lombok.Builder;

@Builder
public record Fotos(
        String fileName,
        String fileType,
        String fotoCliente
) implements java.io.Serializable {
}
