package com.usuarios.service;

import com.usuarios.entities.Empresa;
import com.usuarios.repository.EmpresaRepository;
import com.usuarios.repository.Filter.EmpresaFilter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Page<Empresa> pesquisar(EmpresaFilter empresaFilter, Pageable pageable){
        return empresaRepository.filtrar(empresaFilter, pageable);
    }

    public Empresa buscarPorId(Long id){
        return empresaRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
    }

    public Empresa cadastrar(Empresa empresa){
//        empresa.setSenha(passwordEncoder.encode(empresa.getSenha()));
        return empresaRepository.save(empresa);
    }

    public Empresa atualizar(Long id, Empresa empresa){
        Empresa empresaSalva = buscarPorId(id);
        BeanUtils.copyProperties(empresa, empresaSalva, "id");
        return empresaRepository.save(empresaSalva);
    }

    public void deletar(Long id){
//        this.buscarPorId(id);
        empresaRepository.deleteById(id);
    }

    public void atualizarPropriedadeAtivo(Long id, boolean ativo){
        Empresa empresa = buscarPorId(id);
        empresa.setAtivo(ativo);
        empresaRepository.save(empresa);
        if(!ativo){
            empresaRepository.desabilitarFuncinarioVinculado(empresa.getId());
        }
    }
}
