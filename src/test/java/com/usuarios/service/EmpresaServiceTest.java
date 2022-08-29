package com.usuarios.service;

import com.usuarios.entities.Empresa;
import com.usuarios.repository.EmpresaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class EmpresaServiceTest {

    private Long idExistente;
    private Long idNaoExistente;

    private Empresa empresa;
    private Empresa empresaInvalida;



    @BeforeEach
    void setup() {
        idExistente = 1L;
        idNaoExistente = 1000L;

        empresa = new Empresa();
        empresaInvalida = new Empresa();

        Mockito.doThrow(EmptyResultDataAccessException.class).when(empresaRepository).save(empresaInvalida);
        Mockito.when(empresaRepository.save(empresa)).thenReturn(empresa);

        Mockito.doNothing().when(empresaRepository).deleteById(idExistente);
        Mockito.doThrow(EmptyResultDataAccessException.class).when(empresaRepository).deleteById(idNaoExistente);

        Mockito.when(empresaRepository.findById(idExistente)).thenReturn(Optional.of(empresa));
        Mockito.doThrow(EmptyResultDataAccessException.class).when(empresaRepository).findById(idNaoExistente);

    }


    @InjectMocks
    EmpresaService empresaService;

    @Mock
    EmpresaRepository empresaRepository;

    @Test
    public void returnExceptionWhenEmpresaFail() {
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> empresaService.cadastrar(empresaInvalida));
        Mockito.verify(empresaRepository).save(empresaInvalida);

    }




    @Test
    public void returnFuncionarioWhenSuccess() {
        Empresa empresaTest = empresaService.cadastrar(empresa);
        Assertions.assertNotNull(empresaTest);
        Mockito.verify(empresaRepository).save(empresa);
    }

    @Test
    public void returnExceptionWhenDeleteFail() {
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> empresaService.deletar(idNaoExistente));
        Mockito.verify(empresaRepository, Mockito.times(1)).deleteById(idNaoExistente);
    }



    @Test
    public void returnNothingWhenDeleteSuccess() {
        empresaService.deletar(idExistente);
        Mockito.verify(empresaRepository).deleteById(idExistente);
    }

    @Test
    public void returnExceptionWhenFindByIdFail() {
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> empresaService.buscarPorId(idNaoExistente));
        Mockito.verify(empresaRepository).findById(idNaoExistente);
    }

    @Test
    public void returnFuncionarioWhenFindByIdSuccess() {
        Assertions.assertNotNull(empresaService.buscarPorId(idExistente));
        Mockito.verify(empresaRepository).findById(idExistente);
    }

    @Test
    public void returnExceptionWhenUpdateFail() {
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> empresaService.atualizar(idNaoExistente, empresa));
        Mockito.verify(empresaRepository).findById(idNaoExistente);
    }

    @Test
    public void returnFuncionarioWhenUpdateSuccess() {
        Assertions.assertNotNull(empresaService.atualizar(idExistente, empresa));
        Mockito.verify(empresaRepository).findById(idExistente);
    }


}
