package com.devjoao.passagem.controller;

import com.devjoao.passagem.dto.PassagemRequestDTO;
import com.devjoao.passagem.dto.PassagemResponseDTO;
import com.devjoao.passagem.service.PassagemServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.InvalidPropertiesFormatException;

@RestController
@RequestMapping(value = "/passagem")
@Slf4j
public class PassagemController {

    @Autowired
    private PassagemServiceImpl service;

    @PostMapping("/cadastrarPassagem")
    public ResponseEntity<PassagemResponseDTO> cadastarClientePassagem(@RequestBody PassagemRequestDTO requestDTO) throws InvalidPropertiesFormatException {
        var response = service.cadastroPassagemCliente(requestDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PassagemResponseDTO> buscarCliente(@PathVariable("id") String id) {
        var result = service.bucarClienteCadastrado(id);
        return ResponseEntity.ok(result);

    }

    @GetMapping(value = "/buscarTodosClientes")
    public ResponseEntity<PassagemResponseDTO> buscarTodosCliente() {
        var result = service.buscarTodosClientes();
        return ResponseEntity.ok(result);

    }
}
