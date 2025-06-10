package com.devjoao.passagem.controller;

import com.devjoao.passagem.config.TokenService;
import com.devjoao.passagem.dto.DadosTokenJWT;
import com.devjoao.passagem.dto.UsuarioLoginDTO;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Slf4j
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService service;

    @PostMapping
    public ResponseEntity<?> efetuarLogin(@RequestBody @Valid UsuarioLoginDTO usuarioLoginDTO) {

        var authenticationToken = new UsernamePasswordAuthenticationToken(usuarioLoginDTO.login(), usuarioLoginDTO.senha());
        var authentication = manager.authenticate(authenticationToken);

        //var usuario = (UsuarioSecurity) authentication.getPrincipal();
        var tokenJWT = service.gerarToken(usuarioLoginDTO);
        log.info("Token gerado com sucesso: {} ", tokenJWT);

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}
