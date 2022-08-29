package com.usuarios.repository;

import com.usuarios.entities.Funcionario;
import com.usuarios.repository.funcionario.FuncionarioRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>, FuncionarioRepositoryQuery {

    Optional<Funcionario> findByCpf(String cpf);

}
