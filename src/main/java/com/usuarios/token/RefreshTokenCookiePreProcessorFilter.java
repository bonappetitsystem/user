package com.usuarios.token;

import org.apache.catalina.util.ParameterMap;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.Map;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RefreshTokenCookiePreProcessorFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        if("/oauth/token".equalsIgnoreCase(httpServletRequest.getRequestURI())
        && "refresh_token".equals(httpServletRequest.getParameter("grant_type"))
            && httpServletRequest.getCookies() != null) {
            for(Cookie cookie : httpServletRequest.getCookies()) {
                if(cookie.getName().equals("refreshToken")) {
                    String refreshToken = cookie.getValue();
                    httpServletRequest = new MyServletRequestWrapper(httpServletRequest, refreshToken);
                }
            }
        }
        chain.doFilter(httpServletRequest, response);
    }


    static class MyServletRequestWrapper extends HttpServletRequestWrapper {

        private String refreshToken;

        public MyServletRequestWrapper(HttpServletRequest request, String refreshToken) {
            super(request);
            this.refreshToken = refreshToken;
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            ParameterMap<String, String[]> parameterMap = new ParameterMap(getRequest().getParameterMap());
            parameterMap.put("refresh_token", new String[]{refreshToken});
            parameterMap.setLocked(true);
            return parameterMap;
        }
    }
}
