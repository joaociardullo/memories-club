package com.devjoao.passagem.service;

import com.devjoao.passagem.dto.EnderecoCepDTO;
import com.devjoao.passagem.dto.PassagemRequestDTO;
import com.devjoao.passagem.dto.PassagemResponseDTO;
import com.devjoao.passagem.entity.PassagemEntity;
import com.devjoao.passagem.exceptions.InvalidPropertiesFormatException;
import com.devjoao.passagem.exceptions.NumberFormatException;
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

    public PassagemResponseDTO cadastroPassagemCliente(PassagemRequestDTO requestDTO) throws InvalidPropertiesFormatException {
        log.info("Dados da passagem: [{}] ", requestDTO);

        try {
            var buscarcpf = repository.findByCpf(requestDTO.getCpf());
            if (buscarcpf.get(0).getCpf() == null) {
                throw new RuntimeException("Inserir o CPF");
            }

            if (buscarcpf.get(0).getCpf().equals(requestDTO.getCpf())){
                throw new RuntimeException("CPF já cadastrado");
            }

            if (requestDTO.getCep().length() <= 11 && requestDTO.getCep() == null) {
                throw new NumberFormatException("Cep nao segue o padrão !");
            }
            if (requestDTO.getDiaViagem().equals(null)) {
                throw new InvalidPropertiesFormatException("Obrigatorio passar a data da viagem.");
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
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    private static EnderecoCepDTO getEnderecoCepDTO(EnderecoCepDTO cep) {
        EnderecoCepDTO enderecoCepDTO = EnderecoCepDTO.builder().cep(cep.getCep()).bairro(cep.getBairro()).uf(cep.getUf()).regiao(cep.getRegiao()).estado(cep.getEstado()).localidade(cep.getLocalidade()).logradouro(cep.getLogradouro()).bairro(cep.getBairro()).build();
        return enderecoCepDTO;
    }

    public PassagemResponseDTO toResponseDTO(PassagemEntity passagemEntity) {
        PassagemResponseDTO responseDTO = new PassagemResponseDTO();
        responseDTO.setCode(HttpStatus.CREATED);
        responseDTO.setMessage("Dados cadastrado com sucesso:");
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
