package com.devjoao.passagem.repositorie;

import com.devjoao.passagem.entity.PassagemEntity;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassagemEntityRepository extends JpaRepository<PassagemEntity, Long> {

    @Query(value = "SELECT * FROM cadastro_passagem p WHERE p.cpf = :cpf", nativeQuery = true)
    List<PassagemEntity> findByCpf(@Param ("cpf") String cpf);


}