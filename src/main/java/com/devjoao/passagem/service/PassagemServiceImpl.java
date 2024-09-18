package com.devjoao.passagem.service;

import com.devjoao.passagem.dto.EnderecoCepDTO;
import com.devjoao.passagem.dto.PassagemRequestDTO;
import com.devjoao.passagem.dto.PassagemResponseDTO;
import com.devjoao.passagem.entity.PassagemEntity;
import com.devjoao.passagem.exceptions.CpfException;
import com.devjoao.passagem.exceptions.CpfNullException;
import com.devjoao.passagem.exceptions.IdInvalidException;
import com.devjoao.passagem.exceptions.InvalidPropertiesFormatException;
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

    private final StringProducerService producerService;

    public PassagemServiceImpl(PassagemEntityRepository repository, PassagemMapper mapper, EnderecoClient client, StringProducerService producerService) {
        this.repository = repository;
        this.mapper = mapper;
        this.client = client;
        this.producerService = producerService;
    }

    public PassagemResponseDTO cadastroPassagemCliente(PassagemRequestDTO requestDTO) throws InvalidPropertiesFormatException {
        log.info("Dados da passagem: [{}] ", requestDTO);
        try {

            if (requestDTO.getCep().length() != 11 && requestDTO.getCep() == null) {
                throw new NumberFormatException("Cep nao segue o padrão !");
            }
            if (requestDTO.getCpf().isEmpty()) {
                throw new CpfNullException("Inserir o CPF");
            }
            if (requestDTO.getCpf().length() != 11) {
                throw new CpfNullException("Tamanho de cpf invalido: Digite corretamente");
            }
            if (requestDTO.getDiaViagem() == null) {
                throw new InvalidPropertiesFormatException("Obrigatorio passar a data da viagem.");
            }
            if (requestDTO.getEmail() == null || requestDTO.getEmail().isEmpty() || !requestDTO.getEmail().contains("@")) {
                throw new InvalidPropertiesFormatException("Obrigatorio ter '@' no email.");
            }
            if (requestDTO.getNomeCliente().isBlank()) {
                throw new InvalidPropertiesFormatException("Obrigatorio passar o nome do cliente.");
            }

            var buscarcpf = repository.findByCpf(requestDTO.getCpf());
            if (!buscarcpf.isEmpty()) {
                buscarcpf.get(0).getCpf();
                throw new CpfException("CPF não existe");
            }
            if (!buscarcpf.isEmpty()) {
                if (buscarcpf.get(0).getCpf().equals(requestDTO.getCpf())) {
                    throw new CpfException("CPF já cadastrado");
                }
            }

            log.info("Buscar endereco para cadastramento: [{}] ", requestDTO.getCep());
            EnderecoCepDTO cep = client.buscarEndereco(requestDTO.getCep());

            PassagemEntity passagem = mapper.toEntity(requestDTO);

            EnderecoCepDTO enderecoCepDTO = getEnderecoCepDTO(cep);
            passagem.setCep(enderecoCepDTO);
            PassagemResponseDTO responseDTO = toResponseDTO(passagem);
            log.info("Salvando na Tabela: [{}] ", passagem);
            repository.save(passagem);
            producerService.sendMessage(requestDTO);
            return responseDTO;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    private static EnderecoCepDTO getEnderecoCepDTO(EnderecoCepDTO cep) {
        return EnderecoCepDTO.builder().cep(cep.getCep()).bairro(cep.getBairro()).uf(cep.getUf()).regiao(cep.getRegiao()).estado(cep.getEstado()).localidade(cep.getLocalidade()).logradouro(cep.getLogradouro()).bairro(cep.getBairro()).build();
    }

    public PassagemResponseDTO toResponseDTO(PassagemEntity passagemEntity) {
        PassagemResponseDTO responseDTO = new PassagemResponseDTO();
        responseDTO.setCode(HttpStatus.CREATED);
        responseDTO.setMessage("Dados cadastrado com sucesso:");
        responseDTO.setContent(passagemEntity);
        return responseDTO;
    }

    public PassagemResponseDTO bucarClienteCadastrado(String id) {

        try {
            if (id.contains(" ")) {
                throw new IdInvalidException("id não existe ou o campo nao foi informado");
            }
            if (id.isEmpty()) {
                throw new IdInvalidException("id não existe ou o campo nao foi informado");
            }
            if (id == null) {
                throw new IdInvalidException("id não existe ou o campo nao foi informado");
            }
            var result = repository.findById(Long.valueOf(id));
            if (result.isPresent()) {
                var responseDTO = new PassagemResponseDTO();
                responseDTO.setCode(HttpStatus.ACCEPTED);
                responseDTO.setMessage("Dado buscado com sucesso:");
                responseDTO.setContent(result);
                return responseDTO;
            } else {
                log.error("Erro ao fazer a busca");
                throw new InvalidPropertiesFormatException("erro de sistema");
            }
        } catch (InvalidPropertiesFormatException e) {
            throw new IdInvalidException(e.getMessage());
        }
    }

    public PassagemResponseDTO buscarTodosClientes() {
        log.info("Buscar todos cadastro dos clientes: ");
        var listCadastro = repository.findAll();
        PassagemResponseDTO responseDTO = new PassagemResponseDTO();
        responseDTO.setCode(HttpStatus.OK);
        responseDTO.setMessage("Dado buscado com sucesso:");
        responseDTO.setContent(listCadastro);

        return responseDTO;
    }
}
