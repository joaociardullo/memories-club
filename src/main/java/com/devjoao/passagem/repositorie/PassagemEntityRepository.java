package com.devjoao.passagem.repositorie;

import com.devjoao.passagem.entity.PassagemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassagemEntityRepository extends JpaRepository<PassagemEntity, Long> {
}