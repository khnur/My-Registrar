package com.example.myregistrar.security;

import com.example.myregistrar.dtos.ErrorDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.IOException;
import java.util.Date;

import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service
@RequiredArgsConstructor
public class AuthEntryPoint implements AuthenticationEntryPoint {
    private final RequestMappingHandlerMapping requestMappingHandlerMapping;
    private final ObjectMapper objectMapper;
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        response.setContentType("application/json");

        try {
            requestMappingHandlerMapping.getHandler(request).getHandler();
        } catch (Exception e) {
            response.setStatus(SC_NOT_FOUND);
            response.getWriter().write(
                    objectMapper.writeValueAsString(ErrorDto.builder()
                            .timestamp(new Date())
                            .status(SC_NOT_FOUND)
                            .error(NOT_FOUND.getReasonPhrase())
                            .path(request.getServletPath())
                            .message(e.getMessage())
                            .build())
            );
            return;
        }

        response.setStatus(SC_UNAUTHORIZED);
        response.getWriter().write(
                objectMapper.writeValueAsString(ErrorDto.builder()
                        .timestamp(new Date())
                        .status(SC_UNAUTHORIZED)
                        .error(UNAUTHORIZED.getReasonPhrase())
                        .path(request.getServletPath())
                        .message(authException.getMessage())
                        .build())
        );
    }
}
