package com.library_api.infra.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library_api.exception.ErrorResponseDTO;
import com.library_api.exception.TokenValidationException;
import com.library_api.service.AuthorizationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    TokenService tokenService;
    @Autowired
    AuthorizationService authorizationService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            var token = this.recoverToken(request);
            if(token != null) {
                var subject = tokenService.validateToken(token);
                UserDetails user = authorizationService.loadUserByUsername(subject);

                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication); //passa as informações de autenticação pro context do spring security
            }
            filterChain.doFilter(request, response); //continua a validação dos filtros lá no securityConfiguration
        } catch(TokenValidationException ex){
            handleException(response, ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }

    }

    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ","");// o token na autenticação é passado como "Bearer {token_value}"
    }

    private void handleException(HttpServletResponse response, String message, HttpStatus status) throws IOException{
        response.setStatus(status.value());
        response.setContentType("application/json");
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(message, status);
        String json = new ObjectMapper().writeValueAsString(errorResponseDTO);
        response.getWriter().write(json);
        response.getWriter().flush();
    }
}
