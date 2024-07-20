package com.codigoCerto.tarefas.configs;

import com.codigoCerto.tarefas.exceptions.TokenNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class Filter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        System.out.println(request.getMethod());
        if("/users".equals(requestURI) || "/login".equals(requestURI ) || requestURI.startsWith("/swagger-ui/") ||
                requestURI.startsWith("/v3/api-docs") || requestURI.startsWith("http://trilhabackendjr-jun15-production-e24a.up.railway.app/") || requestURI.startsWith("/favicon.ico")){
            filterChain.doFilter(request,response);
            return;
        }

        var token = findToken(request);
        if (token == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token nao encontrado");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String findToken(HttpServletRequest request) {
        var authorization = request.getHeader("Authorization");
        if (authorization == null){
            throw new TokenNotFoundException();
        }
        return  authorization.replace("Bearer","");
    }
}
