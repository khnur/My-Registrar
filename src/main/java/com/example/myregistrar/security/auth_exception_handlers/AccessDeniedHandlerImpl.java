package com.example.myregistrar.security.auth_exception_handlers;

import com.example.myregistrar.dtos.auth_dto.ErrorDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;
    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        handleException(request, response, accessDeniedException.getMessage(), HttpStatus.FORBIDDEN);
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
