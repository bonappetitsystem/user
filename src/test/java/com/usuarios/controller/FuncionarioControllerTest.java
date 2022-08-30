package com.usuarios.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usuarios.entities.Empresa;
import com.usuarios.entities.Funcionario;
import com.usuarios.entities.Genero;
import com.usuarios.entities.Perfil;
import com.usuarios.service.FuncionarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(webClientEnabled = false)
@WithMockUser
public class FuncionarioControllerTest {


    private Long idExistente;
    private Long idNaoExistente;

    private Funcionario fExistente;
    private Funcionario fNova;

    @Autowired
    private ObjectMapper objMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FuncionarioService funcionarioService;

    @BeforeEach
    void setup() {
        idExistente = 1L;
        idNaoExistente = 100L;

        fNova = new Funcionario();
        fExistente = new Funcionario();
        fExistente.setNome("João");
        fExistente.setCpf("12345678910");
        fExistente.setDataNascimento(LocalDate.of(1990, 1, 1));
        fExistente.setMatricula("123456");
        fExistente.setPerfil(Perfil.GERENCIA);
        fExistente.setSenha("123456");
        fExistente.setGenero(Genero.MASCULINO);
        fExistente.setEmpresa(new Empresa());
        fExistente.setAtivo(true);
        fExistente.setId(1L);


        Mockito.when(funcionarioService.buscarPorId(idExistente)).thenReturn(fExistente);
        Mockito.doThrow(EntityNotFoundException.class).when(funcionarioService).buscarPorId(idNaoExistente);

        Mockito.when(funcionarioService.cadastrar(any())).thenReturn(fExistente);

        Mockito.when(funcionarioService.atualizar(eq(idExistente), any())).thenReturn(fExistente);
        Mockito.when(funcionarioService.atualizar(eq(idNaoExistente), any())).thenThrow(EntityNotFoundException.class);

    }

    @Test
    public void buscarPorIdDeveRetornarFuncionarioQuandoIdExistir() throws Exception {
        ResultActions result = mockMvc.perform(get("/funcionarios/{id}", idExistente)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
    }




    @Test
    public void buscarPorIdDeveRetornarNotFoundQuandoIdNaoExistir() throws Exception {
        ResultActions result = mockMvc.perform(get("/funcionarios/{id}", idNaoExistente)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void cadastrarDeveRetornarFuncionarioQuandoDadosCorretos() throws Exception {
        String jsonBody = objMapper.writeValueAsString(fNova);

        ResultActions result = mockMvc.perform(post("/funcionarios")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").value(idExistente));
    }

    @Test
    public void atualizarDeveRetornarFuncionarioQuandoDadosCorretos() throws Exception {
        String jsonBody = objMapper.writeValueAsString(fNova);

        ResultActions result = mockMvc.perform(put("/funcionarios/{id}", idExistente)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").value(idExistente));
    }

    @Test
    public void atualizarDeveRetornarNotFoundQuandoIdNaoExistir() throws Exception {
        String jsonBody = objMapper.writeValueAsString(fNova);

        ResultActions result = mockMvc.perform(put("/funcionarios/{id}", idNaoExistente)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void deletarDeveRetornarNoContentQuandoIdExistir() throws Exception {
        ResultActions result = mockMvc.perform(delete("/funcionarios/{id}", idExistente)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNoContent());
    }

    @Test
    public void deletarDeveRetornarNotFoundQuandoIdNaoExistir() throws Exception {
        ResultActions result = mockMvc.perform(delete("/funcionarios/{id}", idNaoExistente)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }



}
