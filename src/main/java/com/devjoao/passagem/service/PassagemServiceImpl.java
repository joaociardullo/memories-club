package com.devjoao.passagem.service;

import com.devjoao.passagem.controller.EnderecoController;
import com.devjoao.passagem.dto.EnderecoDTO;
import com.devjoao.passagem.dto.PassagemRequestDTO;
import com.devjoao.passagem.dto.PassagemResponseDTO;
import com.devjoao.passagem.entity.EnderecoEntity;
import com.devjoao.passagem.entity.PassagemEntity;
import com.devjoao.passagem.exceptions.CpfException;
import com.devjoao.passagem.exceptions.IdInvalidException;
import com.devjoao.passagem.exceptions.InvalidPropertiesFormatException;
import com.devjoao.passagem.integration.EnderecoClient;
import com.devjoao.passagem.mappper.PassagemMapper;
import com.devjoao.passagem.repositorie.EnderecoEntityRepository;
import com.devjoao.passagem.repositorie.PassagemEntityRepository;
import com.devjoao.passagem.validatorStrategy.cadastrarStrategy.ValidatorContext;
import com.devjoao.passagem.validatorStrategy.updateStrategy.IdValidator;
import com.devjoao.passagem.validatorStrategy.updateStrategy.PassagemRequestDTOValidator;
import com.devjoao.passagem.validatorStrategy.updateStrategy.ValidatorManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

import static com.devjoao.passagem.utils.ReponseUtils.getPassagemResponseDTO;
import static com.devjoao.passagem.utils.ReponseUtils.toResponseBuscarAll;

@Service
@Slf4j
public class PassagemServiceImpl implements PassagemService {

    public static final String PASSAGEM_NAO_CADASTRADOS = "Passagem não cadastrados";
    final PassagemEntityRepository repository;
    final EnderecoEntityRepository enderecoRepository;
    final PassagemMapper mapper;
    final EnderecoClient client;
    final StringProducerService sendKafka;
    final EnderecoController enderecoController;
    final ValidatorManager validatorManager;


    public PassagemServiceImpl(PassagemMapper mapper, PassagemEntityRepository passagemRepository,
                               EnderecoEntityRepository enderecoRepository,
                               EnderecoClient client,
                               StringProducerService sendKafka,
                               EnderecoController enderecoController,
                               ValidatorManager validatorManager) {
        this.repository = passagemRepository;
        this.enderecoRepository = enderecoRepository;
        this.mapper = mapper;
        this.client = client;
        this.sendKafka = sendKafka;
        this.enderecoController = enderecoController;
        this.validatorManager = validatorManager;
    }

    public PassagemResponseDTO cadastroPassagemCliente(PassagemRequestDTO requestDTO) throws InvalidPropertiesFormatException {
        log.info("Dados da passagem: [{}] ", requestDTO);
        try {
            ValidatorContext validator;
            validator = new ValidatorContext(repository);
            validator.validate(requestDTO);

            consultaValidarCpf(requestDTO);

            EnderecoEntity enderecoSalvo = cadastrarBuscaCepEnderecoClient(requestDTO);
            PassagemEntity passagem = mapper.toPassagem(requestDTO);
            passagem.setDtCadastro(LocalDate.now());
            passagem.setEndereco(enderecoSalvo);

            log.info("Salvando na Tabela: [{}] ", passagem);
            PassagemEntity passagemSalva = repository.save(passagem);
            //para nao mandar para outra fila
            //sendKafka.sendMessage(requestDTO);
            return mapper.toResponse(passagemSalva);

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    private EnderecoEntity cadastrarBuscaCepEnderecoClient(PassagemRequestDTO requestDTO) {
        log.info("Buscar endereco para cadastramento: [{}] ", requestDTO.getCep());
        EnderecoDTO endereco = client.buscarEndereco(requestDTO.getCep());
        EnderecoEntity enderecoMapper = mapper.toEndereco(endereco);
        enderecoMapper.setDtCadastroEndereco(LocalDate.now());
        return enderecoRepository.save(enderecoMapper);
    }

    private void consultaValidarCpf(PassagemRequestDTO requestDTO) {
        if (repository.findByCpf(requestDTO.getCpf()).isPresent())
            log.info("CPF encontrado");
        else
            throw new CpfException("CPF não encontrado");

    }

    @Override
    public PassagemResponseDTO atualizarCadastroCliente(String id, PassagemRequestDTO passagemRequestDTO) {

        validatorManager.addValidator(new IdValidator());
        validatorManager.addValidator(new PassagemRequestDTOValidator());
        validatorManager.validate(id);
        validatorManager.validate(passagemRequestDTO);

        if (Objects.isNull(passagemRequestDTO))
            throw new IdInvalidException(PASSAGEM_NAO_CADASTRADOS);

        repository.findById(Long.valueOf(id))
                .orElseThrow(() -> new IdInvalidException(PASSAGEM_NAO_CADASTRADOS));

        PassagemEntity mapperAtualizar = mapper.toAtualizar(passagemRequestDTO);

        var passagemCadastroAtualizado = repository.save(mapperAtualizar);
        passagemCadastroAtualizado.setDtCadastroAtualizado(LocalDate.now());

        //sendKafka.sendMessage(passagemRequestDTO);

        return mapper.toResponse(passagemCadastroAtualizado);
    }

    @Override
    public PassagemResponseDTO deletar(Long id) {
        repository.deleteById(id);
        return PassagemResponseDTO.builder()
                .code(HttpStatus.OK)
                .message("Cadastro Deletado")
                .content(id)
                .build();
    }

    @Override
    public PassagemResponseDTO buscarClienteCadastrado(String id) {
        try {
            validatorManager.validate(id);

            var result = repository.findById(Long.valueOf(id));
            if (result.isPresent()) {
                return getPassagemResponseDTO(result);
            } else {
                log.error("Erro ao fazer a busca");
                throw new InvalidPropertiesFormatException("erro de sistema, falha ao buscar ID na base de dados");
            }
        } catch (InvalidPropertiesFormatException e) {
            throw new IdInvalidException(e.getMessage());
        }
    }

    @Cacheable("passagem")
    public PassagemResponseDTO buscarTodosClientes() {
        try {
            log.info("Buscar todos cadastro dos clientes: ");
            var listCadastro = repository.findAll();
            return toResponseBuscarAll(listCadastro);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
