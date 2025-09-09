package com.devjoao.passagem.service;

import com.devjoao.passagem.dto.PassagemRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class StringProducerKafkaService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Async
    public void sendMessage(PassagemRequestDTO passagemRequestDTO) {
        if (passagemRequestDTO == null) {
            log.error("Mensagem nula ", passagemRequestDTO);
            throw new RuntimeException();
        } else {
            log.info("[KAFKA SENDER - topic passages] :::: {}", passagemRequestDTO);
            kafkaTemplate.send("str-topic", passagemRequestDTO.toString());
        }
    }
}