package com.devjoao.passagem.service;

import com.devjoao.passagem.dto.EnderecoCepDTO;
import com.devjoao.passagem.dto.PassagemRequestDTO;
import com.devjoao.passagem.dto.PassagemResponseDTO;
import com.devjoao.passagem.entity.PassagemEntity;
import com.devjoao.passagem.integration.EnderecoClient;
import com.devjoao.passagem.mappper.PassagemMapper;
import com.devjoao.passagem.repositorie.PassagemEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PassagemServiceImpl implements PassagemService {

    final PassagemEntityRepository repository;

    final PassagemMapper mapper;

    final EnderecoClient client;

    public PassagemServiceImpl(PassagemEntityRepository repository, PassagemMapper mapper, EnderecoClient client) {
        this.repository = repository;
        this.mapper = mapper;
        this.client = client;
    }

    public PassagemResponseDTO cadastroPassagemCliente(PassagemRequestDTO requestDTO) {
        log.info("Dados da passagem: [{}] ", requestDTO);

        if (requestDTO.equals(null)) {
            log.error("Dados invalido");
            throw new RuntimeException("Dados invalido");
        }

        log.info("Buscar endereco para cadastramento: [{}] ", requestDTO.getCep());
        EnderecoCepDTO cep = client.buscarEndereco(requestDTO.getCep());

        PassagemEntity passagem = mapper.toEntity(requestDTO);

        EnderecoCepDTO enderecoCepDTO = getEnderecoCepDTO(cep);
        passagem.setCep(enderecoCepDTO);
        PassagemResponseDTO responseDTO = toResponseDTO(passagem);
        log.info("Salvando na Tabela: [{}] ", passagem);
        repository.save(passagem);
        return responseDTO;

    }

    private static EnderecoCepDTO getEnderecoCepDTO(EnderecoCepDTO cep) {
        EnderecoCepDTO enderecoCepDTO = EnderecoCepDTO.builder()
                .cep(cep.getCep())
                .bairro(cep.getBairro())
                .uf(cep.getUf())
                .regiao(cep.getRegiao())
                .estado(cep.getEstado())
                .localidade(cep.getLocalidade())
                .logradouro(cep.getLogradouro())
                .bairro(cep.getBairro())
                .build();
        return enderecoCepDTO;
    }

    public PassagemResponseDTO toResponseDTO(PassagemEntity passagemEntity) {
        PassagemResponseDTO responseDTO = new PassagemResponseDTO();
        responseDTO.setCode(HttpStatus.CREATED);
        responseDTO.setMessage("Dado cadastrado com sucesso:");
        responseDTO.setContent(passagemEntity);
        return responseDTO;
    }

    public PassagemResponseDTO bucarClienteCadastrado(String id) {

        var result = repository.findById(Long.valueOf(id));
        PassagemResponseDTO responseDTO = new PassagemResponseDTO();
        responseDTO.setCode(HttpStatus.ACCEPTED);
        responseDTO.setMessage("Dado buscado com sucesso:");
        responseDTO.setContent(result);

        return responseDTO;

    }
}
