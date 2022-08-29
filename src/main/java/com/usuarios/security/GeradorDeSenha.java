package com.usuarios.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeradorDeSenha {
    public static void main(String[] args) {
        System.out.println(gerar());
    }
    public static String gerar() {
        //BCryptPasswordEncoder
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode("123456");
    }
}
