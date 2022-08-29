package com.usuarios.repository;

import com.usuarios.entities.Empresa;
import com.usuarios.repository.empresa.EmpresaRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long>, EmpresaRepositoryQuery {
}
