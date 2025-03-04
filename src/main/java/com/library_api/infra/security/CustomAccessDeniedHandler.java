package com.library_api.infra.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library_api.exception.ErrorResponseDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        ErrorResponseDTO error = new ErrorResponseDTO("You don't have permission to access this feature.", HttpStatus.FORBIDDEN);
        var json = new ObjectMapper().writeValueAsString(error);
        response.getWriter().write(json);
    }
}
