package com.devjoao.passagem.controller;

import com.devjoao.passagem.dto.EnderecoDTO;
import com.devjoao.passagem.integration.EnderecoClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "endereco")
@RequiredArgsConstructor
public class EnderecoController {

    private final EnderecoClient client;

    @GetMapping("/{cep}")
    public ResponseEntity<EnderecoDTO> buscarEndereco(@PathVariable("cep") String cep) {
        var result = client.buscarEndereco(cep);
        return ResponseEntity.ok(result);
    }

}
