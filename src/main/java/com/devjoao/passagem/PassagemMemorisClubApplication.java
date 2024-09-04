package com.devjoao.passagem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PassagemMemorisClubApplication {

    public static void main(String[] args) {
        SpringApplication.run(PassagemMemorisClubApplication.class, args);
    }

}
