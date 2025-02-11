package com.devjoao.passagem.repositorie;

import com.devjoao.passagem.entity.EnderecoEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnderecoEntityRepository extends CrudRepository<EnderecoEntity, Long> {

    @Query(value = "SELECT cp.id AS cadastro_id, cp.endereco_id, " +
            "e.cd_endereco AS endereco_id " +
            "FROM public.cadastro_passagem cp " +
            "JOIN public.endereco e ON cp.endereco_id = e.cd_endereco " +
            "WHERE cp.id = :id", nativeQuery = true)
    Optional<EnderecoEntity> findByIdCadastroAndEndereco(@Param("id") Long id);

}