package com.usuarios.repository.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class ResumoFuncionario {
    private Long id;
    private String nome;
    private String cpf;
    private String genero;
    private LocalDate dataNascimento;
    private String perfil;
    private String matricula;
    private Instant dataCadastro;
}
