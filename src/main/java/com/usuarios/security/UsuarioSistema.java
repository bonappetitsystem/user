package com.usuarios.security;

import com.usuarios.entities.Admin;
import com.usuarios.entities.Funcionario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

public class UsuarioSistema extends User {

    private  Admin admin;
    private Funcionario funcionario;

    public UsuarioSistema(Admin admin, List<GrantedAuthority> authorities) {
        super(admin.getCpf(), admin.getSenha(), authorities);
        this.admin = admin;
    }

    public UsuarioSistema(Funcionario funcionario, List<GrantedAuthority> authorities) {
        super(funcionario.getCpf(), funcionario.getSenha(), authorities);
        this.funcionario = funcionario;
    }

    public Admin getAdmin() {
        return admin;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }


}
