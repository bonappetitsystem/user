package com.usuarios.service;

import com.usuarios.entities.Funcionario;
import com.usuarios.exceptionhandler.customException.EmpresaInexistenteOuInativa;
import com.usuarios.repository.projection.ResumoFuncionario;
import com.usuarios.repository.EmpresaRepository;
import com.usuarios.repository.Filter.FuncionarioFilter;
import com.usuarios.repository.FuncionarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private EmpresaRepository empresaRepository;

    public Page<Funcionario> pesquisar(FuncionarioFilter funcionarioFilter, Pageable pageable){
        return funcionarioRepository.filtrar(funcionarioFilter, pageable);
    }

    public Funcionario buscarPorId(Long id){
        return funcionarioRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
    }

    public Funcionario cadastrar(Funcionario funcionario){
        empresaRepository.findById(funcionario.getEmpresa().getId()).orElseThrow(EmpresaInexistenteOuInativa::new);
        funcionario.setSenha(passwordEncoder.encode(funcionario.getSenha()));
        return funcionarioRepository.save(funcionario);
    }

    public Funcionario atualizar(Long id, Funcionario funcionario){
        Funcionario funcionarioSalvo = buscarPorId(id);
        BeanUtils.copyProperties(funcionario, funcionarioSalvo, "id");
        return funcionarioRepository.save(funcionarioSalvo);
    }

    public void deletar(Long id){
        this.buscarPorId(id);
        funcionarioRepository.deleteById(id);
    }

    public void atualizarPropriedadeAtivo(Long id, boolean ativo){
        Funcionario funcionario = buscarPorId(id);
        funcionario.setAtivo(ativo);
        funcionarioRepository.save(funcionario);
    }


    public Page<ResumoFuncionario> resumir(FuncionarioFilter funcionarioFilter, Pageable pageable) {
        return funcionarioRepository.resumir(funcionarioFilter, pageable);
    }
}
