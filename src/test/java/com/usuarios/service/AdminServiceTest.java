package com.usuarios.service;

import com.usuarios.entities.Admin;
import com.usuarios.entities.Funcionario;
import com.usuarios.repository.AdminRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class AdminServiceTest {

    private Long idExistente;
    private Long idNaoExistente;

    private Admin admin;
    private Admin adminInvalido;


    @BeforeEach
    void setup() {
        idExistente = 1L;
        idNaoExistente = 1000L;

        admin = new Admin();
        adminInvalido = new Admin();

        Mockito.doThrow(IllegalArgumentException.class).when(adminRepository).save(adminInvalido);
        Mockito.when(adminRepository.save(admin)).thenReturn(admin);

        Mockito.doNothing().when(adminRepository).deleteById(idExistente);
        Mockito.doThrow(EmptyResultDataAccessException.class).when(adminRepository).deleteById(idNaoExistente);

        Mockito.when(adminRepository.findById(idExistente)).thenReturn(java.util.Optional.of(admin));
        Mockito.doThrow(EmptyResultDataAccessException.class).when(adminRepository).findById(idNaoExistente);

    }

    @InjectMocks
    AdminService adminService;

    @Mock
    AdminRepository adminRepository;

    @Test
    public void returnExceptionWhenAdminFail() {

        Assertions.assertThrows(IllegalArgumentException.class, () -> adminService.cadastrar(adminInvalido));
        Mockito.verify(adminRepository).save(adminInvalido);

    }


    @Test
    public void returnAdminWhenSuccess() {
        Admin adminTest = adminService.cadastrar(admin);
        Assertions.assertNotNull(adminTest);
        Mockito.verify(adminRepository).save(admin);
    }

    @Test
    public void returnExceptionWhenDeleteFail() {
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> adminService.deletar(idNaoExistente));
        Mockito.verify(adminRepository, Mockito.times(1)).deleteById(idNaoExistente);
    }


    @Test
    public void returnNothingWhenDeleteSuccess() {
        adminService.deletar(idExistente);
        Mockito.verify(adminRepository).deleteById(idExistente);
    }

    @Test
    public void returnExceptionWhenFindByIdFail() {
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> adminService.buscarPorId(idNaoExistente));
        Mockito.verify(adminRepository).findById(idNaoExistente);
    }

    @Test
    public void returnAdminWhenFindByIdSuccess() {
        Assertions.assertNotNull(adminService.buscarPorId(idExistente));
        Mockito.verify(adminRepository).findById(idExistente);
    }

    @Test
    public void returnExceptionWhenUpdateFail() {
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> adminService.atualizar(idNaoExistente, admin));
        Mockito.verify(adminRepository).findById(idNaoExistente);
    }

    @Test
    public void returnAdminWhenUpdateSuccess() {
        Assertions.assertNotNull(adminService.atualizar(idExistente, admin));
        Mockito.verify(adminRepository).findById(idExistente);
    }
}
