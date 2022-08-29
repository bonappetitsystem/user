package com.usuarios.repository.empresa;

import com.usuarios.entities.Empresa;
import com.usuarios.repository.Filter.EmpresaFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmpresaRepositoryQuery {

    Page<Empresa> filtrar(EmpresaFilter empresaFilter, Pageable pageable);

    void desabilitarFuncinarioVinculado(Long id);
}
