package com.devjoao.passagem.controller;

import com.devjoao.passagem.dto.EnderecoCepDTO;
import com.devjoao.passagem.dto.PassagemRequestDTO;
import com.devjoao.passagem.dto.PassagemResponseDTO;
import com.devjoao.passagem.entity.PassagemEntity;
import com.devjoao.passagem.exceptions.InvalidPropertiesFormatException;
import com.devjoao.passagem.integration.EnderecoClient;
import com.devjoao.passagem.mappper.PassagemMapper;
import com.devjoao.passagem.repositorie.PassagemEntityRepository;
import com.devjoao.passagem.service.PassagemServiceImpl;
import com.devjoao.passagem.service.StringProducerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class PassagemControllerTest {

    @InjectMocks
    PassagemController controller;
    @Mock
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
    void test_successfully_register_passagem_with_valid_data() throws InvalidPropertiesFormatException, java.util.InvalidPropertiesFormatException {
        PassagemRequestDTO requestDTO = PassagemRequestDTO.builder()
                .nomeCliente("Joao")
                .sobrenome("Silva")
                .diaViagem("25-12-2023")
                .pais("Brasil")
                .estado("SP")
                .cidade("Sao Paulo")
                .formaPagamento("Cartao")
                .qtdIntegrantes(1)
                .companhiaArea("LATAM")
                .cpf("12345678901")
                .celular("11987654321")
                .cep("12345678")
                .email("joao.silva@example.com")
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

        PassagemResponseDTO responseDTO = new PassagemResponseDTO();
        responseDTO.setCode(HttpStatus.CREATED);
        responseDTO.setMessage("Dados cadastrado com sucesso:");
        responseDTO.setContent(passagemEntity);

        when(mapper.toEntity(requestDTO)).thenReturn(passagemEntity);
        when(client.buscarEndereco(requestDTO.getCep())).thenReturn(enderecoCepDTO);

        var response = controller.cadastarClientePassagem(requestDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}