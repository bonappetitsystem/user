package com.usuarios.security;

import com.usuarios.entities.Admin;
import com.usuarios.entities.Funcionario;
import com.usuarios.repository.AdminRepository;
import com.usuarios.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Primary
@Qualifier
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private AdminRepository adminRepository;



    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException, DataAccessException {

        Optional<Funcionario> func = funcionarioRepository.findByCpf(cpf);
        Optional<Admin> adm = adminRepository.findByCpf(cpf);
        List<GrantedAuthority> authorities = new ArrayList<>();
        System.out.println("func: " + func + " adm: " + adm);
        if (adm.isPresent()) {
            return adminRepository.findByCpf(cpf).map(admin -> {
                authorities.add(new SimpleGrantedAuthority("ROLE_" +admin.getPerfil().name().toUpperCase()));
                return new UsuarioSistema(admin, authorities);
            }).orElseThrow(() -> new UsernameNotFoundException("Usuário e/ou senha inválidos"));

        } else if (func.isPresent()) {
            return funcionarioRepository.findByCpf(cpf).map(funcionario -> {
                authorities.add(new SimpleGrantedAuthority("ROLE_" +funcionario.getPerfil().name().toUpperCase()));
                return new UsuarioSistema(funcionario, authorities);
            }).orElseThrow(() -> new UsernameNotFoundException("Usuário e/ou senha inválidos"));

        } else {
            throw new UsernameNotFoundException("Usuário e/ou senha inválidos");
        }







    }

}


