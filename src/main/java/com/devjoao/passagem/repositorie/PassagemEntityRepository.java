package com.devjoao.passagem.repositorie;

import com.devjoao.passagem.entity.PassagemEntity;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PassagemEntityRepository extends JpaRepository<PassagemEntity, Long> {

    @Query(countQuery = "SELECT p.cpf FROM cadastro_passagem p WHERE CAST(p.cpf AS string) = :cpf", nativeQuery = true)
    Optional<List<PassagemEntity>> findByCpf(@Param("cpf") String cpf);
}