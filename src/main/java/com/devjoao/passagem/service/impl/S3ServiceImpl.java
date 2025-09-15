package com.devjoao.passagem.service.impl;

import com.devjoao.passagem.service.S3Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.nio.file.Paths;

import static com.devjoao.passagem.enums.Buckets.MEUBUCKETPASSAGEM;

@Service
@Slf4j
public class S3ServiceImpl implements S3Service {

    private final S3Client s3Client;

    public S3ServiceImpl(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public void uploadFile(String filePath) {
        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(MEUBUCKETPASSAGEM.getNome())
                        .key(filePath)
                        .build(),
                RequestBody.fromFile(new File(filePath))
        );
        log.info("Arquivo: [{}] foi enviado com sucesso", filePath);
    }

    public void downloadFile(String keyName, String destinationPath) {
        s3Client.getObject(
                GetObjectRequest.builder()
                        .bucket(MEUBUCKETPASSAGEM.getNome())
                        .key(keyName)
                        .build(),
                Paths.get(destinationPath)
        );
    }
}