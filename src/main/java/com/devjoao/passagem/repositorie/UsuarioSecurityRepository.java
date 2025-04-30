package com.devjoao.passagem.repositorie;

import com.devjoao.passagem.entity.UsuarioSecurity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioSecurityRepository extends JpaRepository<UsuarioSecurity, Long> {

    UserDetails findByLogin(String username);
}