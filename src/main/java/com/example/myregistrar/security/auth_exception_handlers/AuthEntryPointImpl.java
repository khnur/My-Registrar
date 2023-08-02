package com.example.myregistrar.security.auth_exception_handlers;

import com.example.myregistrar.dtos.auth_dto.ErrorDto;
import com.example.myregistrar.exceptions.conflict.ModelAlreadyExistsException;
import com.example.myregistrar.exceptions.not_found.ModelNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.*;

@Service
public class AuthEntryPointImpl implements AuthenticationEntryPoint {
    private final HandlerExceptionResolver resolver;
    private final RequestMappingHandlerMapping requestMappingHandlerMapping;
    private final ObjectMapper objectMapper;

    @Autowired
    public AuthEntryPointImpl(
            @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver,
            RequestMappingHandlerMapping requestMappingHandlerMapping,
            ObjectMapper objectMapper
    ) {
        this.resolver = resolver;
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        HttpStatus status = UNAUTHORIZED;
        String exMessage = authException.getMessage();

        try {
            requestMappingHandlerMapping.getHandler(request);
        } catch (ModelNotFoundException e) {
            status = NOT_FOUND;
            exMessage = e.getMessage();
        } catch (ModelAlreadyExistsException e) {
            status = CONFLICT;
            exMessage = e.getMessage();
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
