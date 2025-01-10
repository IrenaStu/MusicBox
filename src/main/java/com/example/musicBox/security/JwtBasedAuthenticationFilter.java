package com.example.musicBox.security;

import com.example.musicBox.facade.AuthFacade;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration @RequiredArgsConstructor
@Qualifier("JwtBasedAuthentication")
public class JwtBasedAuthenticationFilter extends OncePerRequestFilter {
    private final AuthFacade authFacade;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String authenticationHeader  = request.getHeader("Authorization");
            if (authenticationHeader == null){
                System.out.println("Before passing to filter chain: " + request.getServletPath());
                filterChain.doFilter(request, response);
                System.out.println("After passing to filter chain: " + request.getServletPath());
return;
            }
            if ( !authenticationHeader.startsWith("Bearer ")){
                filterChain.doFilter(request,response);
                return;
            }
            String token = authenticationHeader.replace("Bearer ","");
            Authentication authenticate = authFacade.authenticated(token);
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            filterChain.doFilter(request,response);
        }  catch (RuntimeException exception) {
            System.out.println(exception);}
    }
}
