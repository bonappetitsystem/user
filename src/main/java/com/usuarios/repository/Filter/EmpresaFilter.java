package com.usuarios.repository.Filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpresaFilter {
    private String cnpj;
    private String razaoSocial;
    private String nomeFantasia;
}
