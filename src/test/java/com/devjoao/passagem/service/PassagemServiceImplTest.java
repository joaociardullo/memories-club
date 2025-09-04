package com.devjoao.passagem.service;

import com.devjoao.passagem.dto.PassagemRequestDTO;
import com.devjoao.passagem.dto.PassagemResponseDTO;
import com.devjoao.passagem.entity.PassagemEntity;
import com.devjoao.passagem.integration.EnderecoClient;
import com.devjoao.passagem.mappper.PassagemMapper;
import com.devjoao.passagem.repositorie.EnderecoEntityRepository;
import com.devjoao.passagem.repositorie.PassagemEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PassagemServiceImplTest {

    @InjectMocks
    PassagemServiceImpl service;
    @Mock
    PassagemEntityRepository repository;
    @Mock
    PassagemMapper mapper;
    @Mock
    EnderecoClient client;
    @Mock
    StringProducerKafkaService producerService;
    @Mock
    EnderecoEntityRepository enderecoRepository;
    @Mock
    StringProducerKafkaService sendKafka;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    private static PassagemEntity getPassagemEntity(PassagemRequestDTO requestDTO) {
        PassagemEntity passagemEntity = new PassagemEntity();
        passagemEntity.setNomeCliente(requestDTO.getNomeCliente());
        passagemEntity.setSobrenome(requestDTO.getSobrenome());
        passagemEntity.setDiaViagem(requestDTO.getDiaViagem());
        passagemEntity.setPais(requestDTO.getPais());
        passagemEntity.setEstado(requestDTO.getEstado());
        passagemEntity.setCidade(requestDTO.getCidade());
        passagemEntity.setFormaPagamento(requestDTO.getFormaPagamento());
        passagemEntity.setQtdIntegrantes(requestDTO.getQtdIntegrantes());
        passagemEntity.setCompanhiaArea(requestDTO.getCompanhiaArea());
        passagemEntity.setCpf(requestDTO.getCpf());
        passagemEntity.setCelular(requestDTO.getCelular());
        passagemEntity.setEmail(requestDTO.getEmail());
        return passagemEntity;
    }

    @Test
    void test_cadastroPassagemCliente_emptyOrNullCpf() {

        PassagemRequestDTO requestDTO = PassagemRequestDTO.builder()
                .nomeCliente("Joao")
                .sobrenome("Silva")
                .diaViagem("01-01-2023")
                .pais("Brasil")
                .estado("SP")
                .cidade("Sao Paulo")
                .formaPagamento("Cartao")
                .qtdIntegrantes(1)
                .companhiaArea("LATAM")
                .cpf("")
                .celular("11999999999")
                .cep("12345678")
                .email("joao.silva@example.com")
                .build();

        assertThrows(RuntimeException.class, () -> {
            service.cadastroPassagemCliente(requestDTO);
        });

        requestDTO.setCpf(null);

        assertThrows(RuntimeException.class, () -> {
            service.cadastroPassagemCliente(requestDTO);
        });
    }

    @Test
    void test_returns_ok_status_with_data() {
        List<PassagemEntity> mockData = Arrays.asList(new PassagemEntity(), new PassagemEntity());
        when(repository.findAll()).thenReturn(mockData);

        PassagemResponseDTO response = service.buscarTodosClientes();

        assertEquals(HttpStatus.CREATED, response.getCode());
        assertEquals("Dados cadastrado com sucesso:", response.getMessage());
        assertEquals(mockData, response.getContent());
    }

    @Test
    void test_returns_empty_list_when_no_data() {

        List<PassagemEntity> emptyList = Collections.emptyList();
        when(repository.findAll()).thenReturn(emptyList);

        PassagemResponseDTO response = service.buscarTodosClientes();

        assertEquals(HttpStatus.CREATED, response.getCode());
        assertEquals("Dados cadastrado com sucesso:", response.getMessage());
        assertTrue(((List<?>) response.getContent()).isEmpty());
    }

//    @Test
//    void test_valid_id_returns_accepted_status() {
//
//        PassagemEntity passagemEntity = new PassagemEntity();
//        Optional<PassagemEntity> optionalPassagemEntity = Optional.of(passagemEntity);
//        when(repository.findById(1L)).thenReturn(optionalPassagemEntity);
//
//        PassagemResponseDTO response = service.buscarClienteCadastrado("1");
//
//        assertEquals(HttpStatus.CREATED, response.getCode());
//        assertEquals("Dados cadastrado com sucesso:", response.getMessage());
//        assertEquals(optionalPassagemEntity, response.getContent());
//    }

//    @Test
//    void test_id_contains_spaces_throws_exception() {
//
//        assertThrows(IdInvalidException.class, () -> {
//            service.buscarClienteCadastrado("1 2");
//        });
   // }
}