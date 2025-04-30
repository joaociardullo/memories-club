package com.devjoao.passagem.service;

import com.devjoao.passagem.repositorie.UsuarioSecurityRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {

    final UsuarioSecurityRepository usuarioSecurityRepository;

    public AutenticacaoService(UsuarioSecurityRepository usuarioSecurityRepository) {
        this.usuarioSecurityRepository = usuarioSecurityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioSecurityRepository.findByLogin(username);
    }
}
