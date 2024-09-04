package com.devjoao.passagem.controller;

import com.devjoao.passagem.dto.EnderecoCepDTO;
import com.devjoao.passagem.integration.EnderecoClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "endereco")
public class EnderecoController {


    private final EnderecoClient client;

    public EnderecoController(EnderecoClient client) {
        this.client = client;
    }

    @GetMapping("/{cep}")
    private EnderecoCepDTO buscarEndereco(@PathVariable("cep") String cep) {
        return client.buscarEndereco(cep);
    }

}
