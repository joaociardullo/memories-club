package com.devjoao.passagem.controller;

import com.devjoao.passagem.dto.PassagemRequestDTO;
import com.devjoao.passagem.dto.PassagemResponseDTO;
import com.devjoao.passagem.service.PassagemServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/passagem")
@Slf4j
public class PassagemController {

    @Autowired
    private PassagemServiceImpl service;

    @PostMapping("/cadastrarPassagem")
    public PassagemResponseDTO cadastarPassagem(@RequestBody PassagemRequestDTO requestDTO) {
        log.info("PASSAGEM: [{}]", requestDTO);
        var response = service.cadastroPassagemCliente(requestDTO);
        return response;
    }

    @GetMapping("/{id}")
    public PassagemResponseDTO buscarCliente(@PathVariable("id") String id) {
        return service.bucarClienteCadastrado(id);
    }
}
