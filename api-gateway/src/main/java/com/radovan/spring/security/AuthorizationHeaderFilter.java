package com.radovan.spring.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.io.IOException;

public class AuthorizationHeaderFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authHeader = httpRequest.getHeader("Authorization");

        try {
            if (authHeader != null) {
                RequestContextHolder.currentRequestAttributes()
                        .setAttribute("Authorization", authHeader, RequestAttributes.SCOPE_REQUEST);
            }
        } catch (IllegalStateException e) {
            // Ako nema request atributa, samo nastavi chain
            System.out.println("Nije moguće postaviti Authorization header: " + e.getMessage());
        }

        chain.doFilter(request, response);
    }
}