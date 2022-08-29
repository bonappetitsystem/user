package com.usuarios.service;

import com.usuarios.entities.Funcionario;
import com.usuarios.repository.FuncionarioRepository;
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
public class FuncionarioServiceTest {

    private Long idExistente;
    private Long idNaoExistente;

    private Funcionario funcionario;
    private Funcionario funcionarioInvalido;



    @BeforeEach
    void setup() {
        idExistente = 1L;
        idNaoExistente = 1000L;

        funcionario = new Funcionario();
        funcionarioInvalido = new Funcionario();

        Mockito.doThrow(IllegalArgumentException.class).when(funcionarioRepository).save(funcionarioInvalido);
        Mockito.when(funcionarioRepository.save(funcionario)).thenReturn(funcionario);

        Mockito.doNothing().when(funcionarioRepository).deleteById(idExistente);
        Mockito.doThrow(EmptyResultDataAccessException.class).when(funcionarioRepository).deleteById(idNaoExistente);

        Mockito.when(funcionarioRepository.findById(idExistente)).thenReturn(java.util.Optional.of(funcionario));
        Mockito.doThrow(EmptyResultDataAccessException.class).when(funcionarioRepository).findById(idNaoExistente);

    }

    @InjectMocks
    FuncionarioService funcionarioService;

    @Mock
    FuncionarioRepository funcionarioRepository;

    @Test
    public void returnExceptionWhenFuncionarioFail() {
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> funcionarioService.cadastrar(funcionarioInvalido));
        Mockito.verify(funcionarioRepository).save(funcionarioInvalido);

    }



    @Test
    public void returnFuncionarioWhenSuccess() {
        Funcionario funcionarioTest = funcionarioService.cadastrar(funcionario);
        Assertions.assertNotNull(funcionarioTest);
        Mockito.verify(funcionarioRepository).save(funcionario);
    }

    @Test
    public void returnExceptionWhenDeleteFail() {
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> funcionarioService.deletar(idNaoExistente));
        Mockito.verify(funcionarioRepository, Mockito.times(1)).deleteById(idNaoExistente);
    }



    @Test
    public void returnNothingWhenDeleteSuccess() {
        funcionarioService.deletar(idExistente);
        Mockito.verify(funcionarioRepository).deleteById(idExistente);
    }

    @Test
    public void returnExceptionWhenFindByIdFail() {
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> funcionarioService.buscarPorId(idNaoExistente));
        Mockito.verify(funcionarioRepository).findById(idNaoExistente);
    }

    @Test
    public void returnFuncionarioWhenFindByIdSuccess() {
        Assertions.assertNotNull(funcionarioService.buscarPorId(idExistente));
        Mockito.verify(funcionarioRepository).findById(idExistente);
    }

    @Test
    public void returnExceptionWhenUpdateFail() {
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> funcionarioService.atualizar(idNaoExistente, funcionario));
        Mockito.verify(funcionarioRepository).findById(idNaoExistente);
    }

    @Test
    public void returnFuncionarioWhenUpdateSuccess() {
        Assertions.assertNotNull(funcionarioService.atualizar(idExistente, funcionario));
        Mockito.verify(funcionarioRepository).findById(idExistente);
    }





}
