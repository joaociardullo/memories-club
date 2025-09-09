package com.devjoao.passagem.service;

public interface S3Service {

    void uploadFile(String filePath);

    void downloadFile(String keyName, String destinationPath);
}
