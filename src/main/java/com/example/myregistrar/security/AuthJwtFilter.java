package com.example.myregistrar.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthJwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        String jwt;

        if (authHeader == null
                || !authHeader.startsWith("Bearer ")
                || !jwtService.validateJwt((jwt = authHeader.substring(7)))
        ) {
            filterChain.doFilter(request, response);
            return;
        }

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(
                jwtService.getUsernameFromJwt(jwt),
                null,
                List.of(
                        new SimpleGrantedAuthority(jwtService.getRoleFromJwt(jwt))
                )
        ));

        SecurityContextHolder.setContext(securityContext);

        filterChain.doFilter(request, response);
    }
}
