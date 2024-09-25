package com.devjoao.passagem.controller;

import com.devjoao.passagem.dto.PassagemRequestDTO;
import com.devjoao.passagem.dto.PassagemResponseDTO;
import com.devjoao.passagem.service.PassagemServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/passagem")
@Slf4j
public class PassagemController {

    private final PassagemServiceImpl service;

    @PostMapping("/cadastrarPassagem")
    @ApiOperation(value = "Metodo principal para cadastrar cliente novo onde é enviado para uma fila")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PassagemResponseDTO> cadastarClientePassagem(@RequestBody PassagemRequestDTO requestDTO) {
        var response = service.cadastroPassagemCliente(requestDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Metodo para buscar clientes passando seu id")
    public ResponseEntity<PassagemResponseDTO> buscarCliente(@PathVariable("id") String id) {
        MDC.get("traceID");
        return ResponseEntity.ok(service.bucarClienteCadastrado(id));

    }

    @ApiOperation(value = "Busca todos os clientes")
    @GetMapping(value = "/buscarTodosClientes")
    public ResponseEntity<PassagemResponseDTO> buscarTodosCliente() {
        return ResponseEntity.ok(service.buscarTodosClientes());

    }

    @ApiOperation(value = "atualizar cadastro")
    @PutMapping(value = "atualizar/{id}")
    public ResponseEntity<PassagemResponseDTO> atualizarCadastro(@PathVariable String id,
                                                                 @RequestBody PassagemRequestDTO requestDTO) {
        return ResponseEntity.ok(service.atualizarCadastroCliente(id, requestDTO));
    }
}
