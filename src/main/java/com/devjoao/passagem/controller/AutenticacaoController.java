package com.devjoao.passagem.controller;

import com.devjoao.passagem.dto.UsuarioLoginDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    public ResponseEntity efetuarLogin(@RequestBody @Valid UsuarioLoginDTO dto) {

        var token = new UsernamePasswordAuthenticationToken(dto.login(), dto.senha());
        var auth = manager.authenticate(token);

        return ResponseEntity.ok().build();
    }
}
