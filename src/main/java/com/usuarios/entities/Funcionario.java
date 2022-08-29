package com.usuarios.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "funcionario")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    private String nome;

    @Column(unique = true)
    @NotNull
    @NotBlank
    @Size(min=11, max=11)
    private String cpf;

    @Enumerated(EnumType.STRING)
    private Genero genero;

    @NotNull
    @Column(name = "data_nascimento")
    @JsonFormat(pattern =  "dd/MM/yyyy")
    private LocalDate dataNascimento;

    @NotNull
    @NotBlank
    private String matricula;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Perfil perfil;

    @NotNull
    @NotBlank
    @Size(min=6)
    private String senha;

    @NotNull
    private Boolean ativo = true;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_empresa")
    private Empresa empresa;


    @Column(name = "data_cadastro", updatable = false)
    private Instant dataCadastro;

    @Column(name = "data_atualizacao")
    private Instant dataAtualizacao;


    @PrePersist
    public void onInsert() {
        this.dataCadastro = Instant.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.dataAtualizacao = Instant.now();
    }



}
