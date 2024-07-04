package com.codigoCerto.tarefas.configs;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class Filter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if("/users".equals(requestURI) || "/login".equals(requestURI)){
            filterChain.doFilter(request,response);
            return;
        }

        var token = findToken(request);
        filterChain.doFilter(request,response);
    }

    private String findToken(HttpServletRequest request) {
        var authorization = request.getHeader("Authorization");
        if (authorization == null){
            throw new RuntimeException("Token não encontrado");
        }
        return  authorization.replace("Bearer","");
    }
}
