package com.devjoao.passagem.message;

import com.devjoao.passagem.dto.PassagemRequestDTO;
import com.devjoao.passagem.service.StringProducerKafkaService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/producer")
@Slf4j
public class KafkaProducerResource {

    private final StringProducerKafkaService producerService;

    @ApiOperation(value = "Metodo principal para envio de uma kafka fila (memories club consumer)")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<?> sendMessage(@RequestBody PassagemRequestDTO message) {
        log.info("[ENVIO AO KAFKA] - Mensagem enviada para a fila [str-topic]");
        producerService.sendMessage(message);
        log.info("[ENVIO AO KAFKA] - Mensagem enviada com sucesso para a fila [str-topic]");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}