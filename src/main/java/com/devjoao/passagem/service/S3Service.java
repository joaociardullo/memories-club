package com.devjoao.passagem.service;

import com.devjoao.passagem.dto.Fotos;

public interface S3Service {

    void uploadFile(Fotos fotos);

    void downloadFile(String keyName, String destinationPath);
}
