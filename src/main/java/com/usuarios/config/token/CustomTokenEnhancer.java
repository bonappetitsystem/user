package com.usuarios.config.token;

import com.usuarios.security.UsuarioSistema;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("deprecation")
public class CustomTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        UsuarioSistema usuarioSistema = (UsuarioSistema) oAuth2Authentication.getPrincipal();

        Map<String, Object> addInfo = new HashMap<>();
        if(!ObjectUtils.isEmpty(usuarioSistema.getAdmin())){

            addInfo.put("id", usuarioSistema.getAdmin().getId());
            addInfo.put("nome", usuarioSistema.getAdmin().getNome());
            addInfo.put("cpf", usuarioSistema.getAdmin().getCpf());
            addInfo.put("email", usuarioSistema.getAdmin().getEmail());
            addInfo.put("perfil", usuarioSistema.getAdmin().getPerfil().name());


        }

        if(!ObjectUtils.isEmpty(usuarioSistema.getFuncionario())){
            addInfo.put("id", usuarioSistema.getFuncionario().getId());
            addInfo.put("nome", usuarioSistema.getFuncionario().getNome());
            addInfo.put("cpf", usuarioSistema.getFuncionario().getCpf());
            addInfo.put("perfil", usuarioSistema.getFuncionario().getPerfil().name());
        }


        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(addInfo);
        return oAuth2AccessToken;
    }
}
