package com.devjoao.passagem.service;

import com.devjoao.passagem.dto.EnderecoDTO;
import com.devjoao.passagem.dto.PassagemRequestDTO;
import com.devjoao.passagem.dto.PassagemResponseDTO;
import com.devjoao.passagem.entity.EnderecoEntity;
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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
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

    @Test
    void test_valid_request_saves_data_and_returns_success() {
        PassagemRequestDTO requestDTO = PassagemRequestDTO.builder()
                .nomeCliente("João")
                .cpf("12345678901")
                .email("joao@email.com")
                .cep("03380160")
                .diaViagem("01-01-2024")
                .build();

        EnderecoEntity endereco = EnderecoEntity.builder()
                .cdEndereco(1L)
                .cep("12345678")
                .logradouro("Rua Exemplo, 123")
                .bairro("Centro")
                .localidade("São Paulo")
                .uf("SP")
                .estado("São Paulo")
                .regiao("Sudeste")
                .dtCadastroEndereco(LocalDate.of(2026, 2, 25))
                .build();

        PassagemEntity passagem = new PassagemEntity();
        passagem.setId(1L);
        passagem.setNomeCliente("João");
        passagem.setSobrenome("Silva");
        passagem.setDiaViagem("15-08-2024");
        passagem.setPais("Brasil");
        passagem.setEstado("São Paulo");
        passagem.setCidade("São Paulo");
        passagem.setFormaPagamento("Cartão de Crédito");
        passagem.setQtdIntegrantes(2);
        passagem.setCompanhiaArea("Latam");
        passagem.setCpf("12345678901");
        passagem.setCelular("11999999999");
        passagem.setEmail("joao.silva@email.com");
        passagem.setDtCadastro(LocalDate.of(2026, 2, 25));
        passagem.setEndereco(endereco);

        Optional<List<PassagemEntity>> passagemOptional = Optional.of(List.of(passagem));

        endereco.setDtCadastroEndereco(LocalDate.of(2026, 2, 25));

        when(repository.findByCpf(requestDTO.getCpf())).thenReturn(passagemOptional);
        when(client.buscarEndereco(requestDTO.getCep())).thenReturn(EnderecoDTO.builder().build());
        when(enderecoRepository.save(any())).thenReturn(endereco);
        when(mapper.toPassagem(requestDTO)).thenReturn(passagem);
        when(repository.save(any())).thenReturn(passagem);
        when(mapper.toEndereco(any())).thenReturn(endereco);
        when(mapper.toResponse(passagem)).thenReturn(PassagemResponseDTO
                .builder()
                .code(HttpStatus.OK)
                .message("Cadastro realizado com sucesso!")
                .content(passagem)
                .build());

        PassagemResponseDTO response = service.cadastroPassagemCliente(requestDTO);

        verify(repository).save(any());
        assertNotNull(response);
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