package com.usuarios.controller;

import com.usuarios.entities.Empresa;
import com.usuarios.repository.Filter.EmpresaFilter;
import com.usuarios.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/empresas")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @GetMapping
//    @PreAuthorize("hasAuthority('ROLE_ADMIN') and hasAuthority('SCOPE_read')")
    public Page<Empresa> pesquisar(EmpresaFilter empresaFilter, Pageable pageable) {
        return empresaService.pesquisar(empresaFilter, pageable);
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN') and hasAuthority('SCOPE_read')")
    public ResponseEntity<Empresa> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(empresaService.buscarPorId(id));
    }

    @PostMapping
//    @PreAuthorize("hasAuthority('ROLE_ADMIN') and hasAuthority('SCOPE_write')")
    public ResponseEntity<Empresa> cadastrar(@Valid @RequestBody Empresa empresa){
        return ResponseEntity.status(HttpStatus.CREATED).body(empresaService.cadastrar(empresa));
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN') and hasAuthority('SCOPE_write')")
    public ResponseEntity<Empresa> deletar(@PathVariable Long id){
        empresaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN') and hasAuthority('SCOPE_write')")
    public ResponseEntity<Empresa> atualizar(@PathVariable Long id, @RequestBody Empresa empresa){
        return ResponseEntity.ok(empresaService.atualizar(id, empresa));
    }

    @PutMapping("/{id}/ativo")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN') and hasAuthority('SCOPE_write')")
    public ResponseEntity<Empresa> atualizarPropriedadeAtivo(@PathVariable Long id, @Valid @RequestBody boolean ativo){
        empresaService.atualizarPropriedadeAtivo(id, ativo);
        return ResponseEntity.noContent().build();
    }


}
