package com.example.myregistrar.config;

import com.example.myregistrar.security.AuthJwtFilter;
import com.example.myregistrar.util.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity httpSecurity,
            AuthJwtFilter authJwtFilter,
            AuthenticationEntryPoint authEntryPoint,
            AuthenticationProvider authenticationProvider,
            AuthenticationManager authenticationManager,
            AccessDeniedHandler accessDeniedHandler
    ) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers(toH2Console()).permitAll()
                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers("/completable-future/**").permitAll()
                                .requestMatchers("/student/**").permitAll()  /////// DEL
                                .requestMatchers(PUT).hasRole(Role.ADMIN.name())
                                .requestMatchers(POST).hasRole(Role.ADMIN.name())
                                .requestMatchers(DELETE).hasRole(Role.ADMIN.name())
//                        .requestMatchers("/student/**").hasAnyRole(Role.STUDENT.name(), Role.ADMIN.name())
//                        .requestMatchers("/university/**").hasRole(Role.ADMIN.name())
//                        .requestMatchers("/course/**").hasAnyRole(Role.ADMIN.name())
//                        .requestMatchers("/book/**").hasAnyRole(Role.ADMIN.name())
                                .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .exceptionHandling(exception -> {
                    exception.authenticationEntryPoint(authEntryPoint);
                    exception.accessDeniedHandler(accessDeniedHandler);
                })

                .addFilterBefore(authJwtFilter, UsernamePasswordAuthenticationFilter.class)

                .authenticationProvider(authenticationProvider)
                .authenticationManager(authenticationManager)

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/logout?logout")
                        .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.NO_CONTENT))
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("jwt")
                )
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder
    ) {
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails admin = User.withUsername("admin")
                .password("$2a$12$BO709/LgaeJi4N2Q0wlEo.5NetvwKO9hZgJYfnDhuQmQWeziyFgh.")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(admin);
    }
}
