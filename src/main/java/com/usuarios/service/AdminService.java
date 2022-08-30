package com.usuarios.service;

import com.usuarios.entities.Admin;
import com.usuarios.repository.AdminRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    public List<Admin> buscarTodos() {
        return adminRepository.findAll();
    }

    public Admin buscarPorId(Long id) {
        return adminRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
    }

    public Admin cadastrar(Admin admin) {
//        admin.setSenha(passwordEncoder.encode(admin.getSenha()));
        return adminRepository.save(admin);
    }

    public void deletar(Long id) {
//        buscarPorId(id);
        adminRepository.deleteById(id);
    }

    public Admin atualizar(Long id, Admin admin) {
        Admin adminSalvo = buscarPorId(id);
        BeanUtils.copyProperties(admin, adminSalvo, "id");
        return adminRepository.save(adminSalvo);
    }

    public Admin atualizarPropriedateAtivo(Long id, Boolean ativo) {
        Admin admin = buscarPorId(id);
        admin.setAtivo(ativo);
        return adminRepository.save(admin);
    }


}
