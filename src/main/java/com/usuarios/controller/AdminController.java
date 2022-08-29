package com.usuarios.controller;

import com.usuarios.entities.Admin;
import com.usuarios.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN') and hasAuthority('SCOPE_read')")
    public List<Admin> buscarTodos() {
        return adminService.buscarTodos();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') and hasAuthority('SCOPE_write')")
    public ResponseEntity<Admin> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN') and hasAuthority('SCOPE_write')")
    public ResponseEntity<Admin> salvar(@Valid @RequestBody Admin admin) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.cadastrar(admin));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') and hasAuthority('SCOPE_write')")
    public ResponseEntity<Admin> atualizar(@PathVariable Long id, @Valid @RequestBody Admin admin) {
        return ResponseEntity.ok(adminService.atualizar(id, admin));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') and hasAuthority('SCOPE_write')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        adminService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/ativo")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') and hasAuthority('SCOPE_write')")
    public ResponseEntity<Admin> atualizarPropriedateAtivo(@PathVariable Long id, @RequestBody Boolean ativo) {
        return ResponseEntity.ok(adminService.atualizarPropriedateAtivo(id, ativo));
    }
}
