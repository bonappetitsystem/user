package com.usuarios.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Email;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class Contato {
    private String telefone;
    @Column(unique = true)
    @Email
    private String email;
    private String site;

    private String whatsapp;
}
