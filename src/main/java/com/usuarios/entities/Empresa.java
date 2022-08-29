package com.usuarios.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "empresa")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull
    @NotBlank
    @Size(min = 14, max = 14)
    private String cnpj;

    @Column(name = "razao_social")
    @NotNull
    @NotBlank
    private String RazaoSocial;

    @Column(name = "nome_fantasia")
    @NotNull
    @NotBlank
    private String nomeFantasia;

    @Column(name = "inscricao_estadual")
    @NotNull
    @NotBlank
    private String inscricaoEstadual;

    @Column(name = "inscricao_municipal")
    @NotNull
    @NotBlank
    private String inscricaoMunicipal;

    private String sigla;

    @Embedded
    private Endereco endereco;

    @Embedded
    private Contato contato;

    @NotNull
    @NotBlank
    @Size(min = 6)
    private String senha;

    @Column(name = "data_cadastro", updatable = false)
    private Instant dataCadastro;
    @Column(name = "data_atualizacao")
    private Instant dataAtualizacao;

    @NotNull
    private Boolean ativo = true;

    @Transient
    @JsonIgnore
    public boolean isInativo(){
        return !this.ativo;
    }

    @PrePersist
    public void onInsert() {
        this.dataCadastro = Instant.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.dataAtualizacao = Instant.now();
    }


}
