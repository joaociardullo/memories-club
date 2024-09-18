package com.devjoao.passagem.service;

import com.devjoao.passagem.dto.EnderecoCepDTO;
import com.devjoao.passagem.dto.PassagemRequestDTO;
import com.devjoao.passagem.dto.PassagemResponseDTO;
import com.devjoao.passagem.entity.PassagemEntity;
import com.devjoao.passagem.exceptions.IdInvalidException;
import com.devjoao.passagem.exceptions.InvalidPropertiesFormatException;
import com.devjoao.passagem.integration.EnderecoClient;
import com.devjoao.passagem.mappper.PassagemMapper;
import com.devjoao.passagem.repositorie.PassagemEntityRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    StringProducerService producerService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new PassagemServiceImpl(repository, mapper, client, producerService);
    }


    @Test
    void test_successful_registration_with_valid_data() {

        PassagemRequestDTO requestDTO = PassagemRequestDTO.builder()
                .nomeCliente("John Doe")
                .cpf("12345678901")
                .cep("03380160")
                .diaViagem("01-01-2023")
                .email("john.doe@example.com")
                .build();

        EnderecoCepDTO enderecoCepDTO = EnderecoCepDTO.builder()
                .cep("03380160")
                .logradouro("teste")
                .localidade("SP")
                .bairro("SP")
                .regiao("SP")
                .uf("SP")
                .build();
        PassagemEntity passagemEntity = new PassagemEntity();
        passagemEntity.setCpf(enderecoCepDTO.getCep());

        when(client.buscarEndereco(requestDTO.getCep())).thenReturn(enderecoCepDTO);
        when(mapper.toEntity(requestDTO)).thenReturn(passagemEntity);
        when(repository.findByCpf(requestDTO.getCpf())).thenReturn(List.of());

        PassagemResponseDTO responseDTO = service.cadastroPassagemCliente(requestDTO);

        assertEquals(HttpStatus.CREATED, responseDTO.getCode());
        assertEquals("Dados cadastrado com sucesso:", responseDTO.getMessage());
        assertNotNull(responseDTO.getContent());
    }

    @SneakyThrows
    @Test
    void test_cadastroPassagemCliente_success() throws InvalidPropertiesFormatException {
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
                .cpf("12345678901")
                .celular("11999999999")
                .cep("12345678")
                .email("joao.silva@example.com")
                .build();

        EnderecoCepDTO enderecoCepDTO = EnderecoCepDTO.builder()
                .cep("12345678")
                .bairro("Centro")
                .uf("SP")
                .regiao("Sudeste")
                .estado("Sao Paulo")
                .localidade("Sao Paulo")
                .logradouro("Rua A")
                .build();

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
        passagemEntity.setCep(enderecoCepDTO);
        passagemEntity.setEmail(requestDTO.getEmail());

        when(client.buscarEndereco(requestDTO.getCep())).thenReturn(enderecoCepDTO);
        when(mapper.toEntity(requestDTO)).thenReturn(passagemEntity);
        when(repository.save(passagemEntity)).thenReturn(passagemEntity);

        PassagemResponseDTO response = service.cadastroPassagemCliente(requestDTO);

        assertEquals(HttpStatus.CREATED, response.getCode());
        assertEquals("Dados cadastrado com sucesso:", response.getMessage());
        assertEquals(passagemEntity, response.getContent());
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

        assertEquals(HttpStatus.OK, response.getCode());
        assertEquals("Dado buscado com sucesso:", response.getMessage());
        assertEquals(mockData, response.getContent());
    }

    @Test
    void test_returns_empty_list_when_no_data() {

        List<PassagemEntity> emptyList = Collections.emptyList();
        when(repository.findAll()).thenReturn(emptyList);

        PassagemResponseDTO response = service.buscarTodosClientes();

        assertEquals(HttpStatus.OK, response.getCode());
        assertEquals("Dado buscado com sucesso:", response.getMessage());
        assertTrue(((List<?>) response.getContent()).isEmpty());
    }

    @Test
    void test_valid_id_returns_accepted_status() {

        PassagemEntity passagemEntity = new PassagemEntity();
        Optional<PassagemEntity> optionalPassagemEntity = Optional.of(passagemEntity);
        when(repository.findById(1L)).thenReturn(optionalPassagemEntity);

        PassagemResponseDTO response = service.bucarClienteCadastrado("1");

        assertEquals(HttpStatus.ACCEPTED, response.getCode());
        assertEquals("Dado buscado com sucesso:", response.getMessage());
        assertEquals(optionalPassagemEntity, response.getContent());
    }

    @Test
    void test_id_contains_spaces_throws_exception() {

        assertThrows(IdInvalidException.class, () -> {
            service.bucarClienteCadastrado("1 2");
        });
    }
}