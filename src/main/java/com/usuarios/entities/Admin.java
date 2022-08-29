package com.usuarios.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "admin")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    @NotBlank
    @NotNull
    private String cpf;
    @NotBlank
    @NotNull
    private String nome;
    @NotBlank
    @NotNull
    private String senha;
    @Column(unique = true)
    @NotBlank
    @NotNull
    @Email
    private String email;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Perfil perfil = Perfil.ADMIN;
    @NotNull
    private Boolean ativo = true;
    @Column(name = "data_cadastro", updatable = false)
    private Instant dataCadastro;
    @Column(name = "data_atualizacao")
    private Instant dataAtualizacao;

    @PrePersist
    public void prePersist() {
        this.dataCadastro = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.dataAtualizacao = Instant.now();
    }

}
