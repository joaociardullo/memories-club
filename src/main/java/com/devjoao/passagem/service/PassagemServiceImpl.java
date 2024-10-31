package com.devjoao.passagem.service;

import com.devjoao.passagem.dto.EnderecoCepDTO;
import com.devjoao.passagem.dto.PassagemRequestDTO;
import com.devjoao.passagem.dto.PassagemResponseDTO;
import com.devjoao.passagem.entity.PassagemEntity;
import com.devjoao.passagem.exceptions.CpfException;
import com.devjoao.passagem.exceptions.IdInvalidException;
import com.devjoao.passagem.exceptions.InvalidPropertiesFormatException;
import com.devjoao.passagem.integration.EnderecoClient;
import com.devjoao.passagem.mappper.PassagemMapper;
import com.devjoao.passagem.repositorie.PassagemEntityRepository;
import com.devjoao.passagem.validatorStrategy.ValidationStrategyCadastrar;
import com.devjoao.passagem.validatorStrategy.ValidationManagerStratagy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@Slf4j
public class PassagemServiceImpl implements PassagemService {

    public static final String ID_NAO_EXISTE_OU_O_CAMPO_NAO_FOI_INFORMADO = "id não existe ou o campo nao foi informado";
    final PassagemEntityRepository repository;
    final PassagemMapper mapper;
    final EnderecoClient client;
    final ValidationManagerStratagy validationManagerStratagy;

    private final StringProducerService producerService;

    public PassagemServiceImpl(PassagemEntityRepository repository, PassagemMapper mapper, EnderecoClient client, ValidationManagerStratagy validationManagerStratagy, StringProducerService producerService) {
        this.repository = repository;
        this.mapper = mapper;
        this.client = client;
        this.validationManagerStratagy = validationManagerStratagy;
        this.producerService = producerService;
    }

    public PassagemResponseDTO cadastroPassagemCliente(PassagemRequestDTO requestDTO) throws InvalidPropertiesFormatException {
        log.info("Dados da passagem: [{}] ", requestDTO);
        try {
            new ValidationManagerStratagy(Arrays.asList(
                    new ValidationStrategyCadastrar(),
                    new ValidationStrategyCadastrar.CpfValidationStrategy(),
                    new ValidationStrategyCadastrar.DiaViagemValidationStrategy(),
                    new ValidationStrategyCadastrar.EmailValidationStrategy(),
                    new ValidationStrategyCadastrar.NomeClienteValidationStrategy(),
                    new ValidationStrategyCadastrar.CpfExistenteValidationStrategy(repository)
            ));
            validationManagerStratagy.validate(requestDTO);

            var buscarcpf = repository.findByCpf(requestDTO.getCpf());
            if (!buscarcpf.isEmpty()) {
                buscarcpf.get(0).getCpf();
                throw new CpfException("CPF não existe");
            }
            if (!buscarcpf.isEmpty() && buscarcpf.get(0).getCpf().equals(requestDTO.getCpf())) {
                throw new CpfException("CPF já cadastrado");
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

    public PassagemResponseDTO toResponseDTOUpdate(PassagemEntity passagemEntity) {
        PassagemResponseDTO responseDTO = new PassagemResponseDTO();
        responseDTO.setCode(HttpStatus.ACCEPTED);
        responseDTO.setMessage("Dados cadastrado com sucesso:");
        responseDTO.setContent(passagemEntity);
        return responseDTO;
    }

    @Override
    public PassagemResponseDTO atualizarCadastroCliente(String id, PassagemRequestDTO requestDTO) {
        log.info("PASSEI AQUI");
        if (id.contains(" ")) {
            throw new IdInvalidException(ID_NAO_EXISTE_OU_O_CAMPO_NAO_FOI_INFORMADO);

        }
        if (id == null) {
            throw new IdInvalidException(ID_NAO_EXISTE_OU_O_CAMPO_NAO_FOI_INFORMADO);
        }
        if (Objects.isNull(requestDTO)) {
            throw new IdInvalidException("Passagem não cadastrados");
        }

        var consultaCliente = repository.findById(Long.valueOf(id));
        if (consultaCliente.isEmpty()) {
            throw new IdInvalidException("Passagem não cadastrados");
        }
//atualizar
        atualizarCadastroParametros(requestDTO, consultaCliente);

        var save = repository.save(consultaCliente.get());
        return toResponseDTOUpdate(save);
    }

    private static void atualizarCadastroParametros(PassagemRequestDTO requestDTO, Optional<PassagemEntity> consultClient) {
        consultClient.get().setEmail(requestDTO.getEmail());
        consultClient.get().setNomeCliente(requestDTO.getNomeCliente());
        consultClient.get().setSobrenome(requestDTO.getSobrenome());
        consultClient.get().setPais(requestDTO.getPais());
        consultClient.get().setEstado(requestDTO.getEstado());
        consultClient.get().setCidade(requestDTO.getCidade());
        consultClient.get().setFormaPagamento(requestDTO.getFormaPagamento());
        consultClient.get().setCompanhiaArea(requestDTO.getCompanhiaArea());
        consultClient.get().setCpf(requestDTO.getCpf());
        consultClient.get().setEmail(requestDTO.getEmail());
        consultClient.get().setCelular(requestDTO.getCelular());
        consultClient.get().setDiaViagem(requestDTO.getDiaViagem());
        consultClient.get().setQtdIntegrantes(requestDTO.getQtdIntegrantes());
    }

    @Override
    public PassagemResponseDTO bucarClienteCadastrado(String id) {

        try {
            if (id.contains(" ")) {
                throw new IdInvalidException(ID_NAO_EXISTE_OU_O_CAMPO_NAO_FOI_INFORMADO);
            }
            if (id.isEmpty()) {
                throw new IdInvalidException(ID_NAO_EXISTE_OU_O_CAMPO_NAO_FOI_INFORMADO);
            }
            if (id == null) {
                throw new IdInvalidException(ID_NAO_EXISTE_OU_O_CAMPO_NAO_FOI_INFORMADO);
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
        return toResponseBuscarAll(listCadastro);
    }

    private static PassagemResponseDTO toResponseBuscarAll(List<PassagemEntity> listCadastro) {
        PassagemResponseDTO responseDTO = new PassagemResponseDTO();
        responseDTO.setCode(HttpStatus.OK);
        responseDTO.setMessage("Dado buscado com sucesso:");
        responseDTO.setContent(listCadastro);
        return responseDTO;

    }

    public String uploadsArquivo(MultipartFile file) throws IOException {
        log.info("upload de arquivo ", file);
        String filePath = "/uploads/" + file.getOriginalFilename();
        File dest = new File(filePath);
        file.transferTo(dest);
        return filePath;
    }

}
