package com.devjoao.passagem.message;

import com.devjoao.passagem.dto.PassagemRequestDTO;
import com.devjoao.passagem.service.StringProducerService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/producer")
public class KafkaProducerResource {

    private final StringProducerService producerService;

    @ApiOperation(value = "Metodo principal para envio de uma kafka fila (memories club consumer)")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<?> sendMessage(@RequestBody PassagemRequestDTO message) {
        producerService.sendMessage(message);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}