package com.devjoao.passagem.integration;

import com.devjoao.passagem.dto.EnderecoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "endereco", url = "viacep.com.br/ws")
public interface EnderecoClient {

    @GetMapping("/{cep}/json/")
    EnderecoDTO buscarEndereco(@PathVariable("cep") String cep);
}
