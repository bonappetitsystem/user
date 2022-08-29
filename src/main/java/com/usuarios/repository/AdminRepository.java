package com.usuarios.repository;

import com.usuarios.entities.Admin;
import com.usuarios.entities.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByCpf(String cpf);
}
