package com.devjoao.passagem.controller;

import com.devjoao.passagem.dto.PassagemRequestDTO;
import com.devjoao.passagem.dto.PassagemResponseDTO;
import com.devjoao.passagem.integration.EnderecoClient;
import com.devjoao.passagem.mappper.PassagemMapper;
import com.devjoao.passagem.repositorie.EnderecoEntityRepository;
import com.devjoao.passagem.repositorie.PassagemEntityRepository;
import com.devjoao.passagem.service.PassagemServiceImpl;
import com.devjoao.passagem.service.StringProducerKafkaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PassagemControllerTest {

    @InjectMocks
    PassagemController controller;
    @Mock
    PassagemServiceImpl service;
    @Mock
    PassagemEntityRepository repository;
    @Mock
    EnderecoEntityRepository enderecoRepository;
    @Mock
    PassagemMapper mapper;
    @Mock
    EnderecoClient client;
    @Mock
    StringProducerKafkaService sendKafka;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    // Successfully create new passenger booking with valid request data
    @Test
    public void test_create_passenger_booking_success() {
        PassagemRequestDTO requestDTO = PassagemRequestDTO.builder()
                .nomeCliente("John")
                .sobrenome("Doe")
                .cpf("12345678901")
                .email("john@test.com")
                .cep("12345678")
                .diaViagem("01-01-2024")
                .build();

        PassagemResponseDTO responseDTO = PassagemResponseDTO.builder()
                .code(HttpStatus.OK)
                .message("Success")
                .build();

        when(service.cadastroPassagemCliente(requestDTO)).thenReturn(responseDTO);

        ResponseEntity<PassagemResponseDTO> response = controller.cadastarClientePassagem(requestDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
        verify(service).cadastroPassagemCliente(requestDTO);
    }

    // Returns 200 OK with PassagemResponseDTO when valid ID is provided
    @Test
    void test_buscar_cliente_returns_200_ok_with_valid_id() {
        PassagemServiceImpl serviceMock = mock(PassagemServiceImpl.class);
        PassagemController controller = new PassagemController(serviceMock);
        String validId = "123";
        PassagemResponseDTO expectedResponse = PassagemResponseDTO.builder()
                .code(HttpStatus.OK)
                .message("Success")
                .content("Test content")
                .build();

        when(serviceMock.buscarClienteCadastrado(validId)).thenReturn(expectedResponse);

        ResponseEntity<PassagemResponseDTO> response = controller.buscarCliente(validId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
        verify(serviceMock).buscarClienteCadastrado(validId);
    }

}