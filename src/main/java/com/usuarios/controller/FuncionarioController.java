package com.usuarios.controller;

import com.usuarios.entities.Funcionario;
import com.usuarios.repository.projection.ResumoFuncionario;
import com.usuarios.repository.Filter.FuncionarioFilter;
import com.usuarios.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/funcionarios")
@CrossOrigin(maxAge = 10, origins = "http://localhost:8000")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_GERENCIA') and hasAuthority('SCOPE_read')")
    public Page<Funcionario> pesquisar(FuncionarioFilter funcionarioFilter, Pageable pageable){
        return funcionarioService.pesquisar(funcionarioFilter, pageable);
    }

    @GetMapping(params = "resumo")
    @PreAuthorize("hasAuthority('ROLE_GERENCIA') and hasAuthority('SCOPE_read')")
        public Page<ResumoFuncionario> resumir(FuncionarioFilter funcionarioFilter, Pageable pageable){
        return funcionarioService.resumir(funcionarioFilter, pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_GERENCIA') and hasAuthority('SCOPE_read')")
    public ResponseEntity<Funcionario> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(funcionarioService.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_GERENCIA') and hasAuthority('SCOPE_write')")
    public ResponseEntity<Funcionario> cadastrar(@Valid @RequestBody Funcionario funcionario){
        return ResponseEntity.status(HttpStatus.CREATED).body(funcionarioService.cadastrar(funcionario));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_GERENCIA') and hasAuthority('SCOPE_write')")
    public ResponseEntity<Funcionario> atualizar(@PathVariable Long id, @Valid @RequestBody Funcionario funcionario){
        return ResponseEntity.ok(funcionarioService.atualizar(id, funcionario));
    }

    @PutMapping("/{id}/ativo")
    @PreAuthorize("hasAuthority('ROLE_GERENCIA') and hasAuthority('SCOPE_write')")
    public ResponseEntity<Funcionario> atualizarPropriedadeAtivo(@PathVariable Long id, @Valid @RequestBody boolean ativo){
        funcionarioService.atualizarPropriedadeAtivo(id, ativo);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_GERENCIA') and hasAuthority('SCOPE_write')")
    public ResponseEntity<Funcionario> deletar(@PathVariable Long id){
        funcionarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
