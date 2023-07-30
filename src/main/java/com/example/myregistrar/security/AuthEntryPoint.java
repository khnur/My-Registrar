package com.example.myregistrar.security;

import com.example.myregistrar.dtos.auth_dto.ErrorDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.IOException;
import java.time.LocalDateTime;

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.HttpStatus.*;

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

        HttpStatus status = NOT_FOUND;
        String exMessage = authException.getMessage();

        try {
            requestMappingHandlerMapping.getHandler(request).getHandler();
        } catch (IllegalArgumentException e) {
            status = BAD_REQUEST;
            exMessage = e.getMessage();
        } catch (Exception e) {
            status = INTERNAL_SERVER_ERROR;
            exMessage = e.getMessage();
        }

        handleException(request, response, exMessage, status);
    }

    private void handleException(
            HttpServletRequest request,
            HttpServletResponse response,
            String exMessage,
            HttpStatus status
    ) throws IOException {
        response.setStatus(status.value());
        response.getWriter().write(
                objectMapper.writeValueAsString(ErrorDto.builder()
                        .timestamp(LocalDateTime.now())
                        .status(status.value())
                        .error(status.getReasonPhrase())
                        .path(request.getServletPath())
                        .message(exMessage)
                        .build()
                )
        );
    }
}
