package com.usuarios.cors;

import com.usuarios.config.property.UsuariosApiProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;


@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsConfig implements Filter {

//    @Bean
//    public FilterRegistrationBean<CorsFilter> corsFilterRegistrationBean() {
//        CorsConfiguration config = new CorsConfiguration();
//
//        config.setAllowedOrigins(Collections.singletonList("*"));
//        config.setAllowedMethods(Collections.singletonList("*"));
//        config.setAllowedHeaders(Collections.singletonList("*"));
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", config);
//
//        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>();
//        bean.setFilter(new CorsFilter(source));
//        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
//
//        return bean;
//    }
//}
//@Component
//@Order(Ordered.HIGHEST_PRECEDENCE)
//public class CorsFilter implements Filter {
//
    @Autowired
    private UsuariosApiProperty usuariosApiProperty;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String orginPermitida = usuariosApiProperty.getOriginPermitida();
        res.setHeader("Access-Control-Allow-Origin", orginPermitida);
        res.setHeader("Access-Control-Allow-Credentials", "true");

        if("OPTIONS".equals(req.getMethod()) && orginPermitida.equals(req.getHeader("Origin"))) {
            res.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
            res.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept");
            res.setHeader("Access-Control-Max-Age", "3600");
            res.setStatus(HttpServletResponse.SC_OK);
        }else{
            chain.doFilter(request, response);
        }
    }
}
