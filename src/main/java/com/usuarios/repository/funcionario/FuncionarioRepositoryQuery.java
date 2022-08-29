package com.usuarios.repository.funcionario;

import com.usuarios.entities.Funcionario;
import com.usuarios.repository.projection.ResumoFuncionario;
import com.usuarios.repository.Filter.FuncionarioFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FuncionarioRepositoryQuery {

    Page<Funcionario> filtrar(FuncionarioFilter funcionarioFilter, Pageable pageable);
    Page<ResumoFuncionario> resumir(FuncionarioFilter funcionarioFilter, Pageable pageable);
}
