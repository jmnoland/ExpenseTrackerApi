package com.jmnoland.expensetrackerapi;

import com.jmnoland.expensetrackerapi.interfaces.services.AuthenticationServiceInterface;
import com.jmnoland.expensetrackerapi.security.ApiKeyAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           AuthenticationServiceInterface authenticationService) throws Exception {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(
                Stream.of("Authorization", "Cache-Control", "Content-Type", "x-client-id").collect(Collectors.toList())
        );
        corsConfiguration.setAllowedOrigins(
                Stream.of("*").collect(Collectors.toList())
        );
        corsConfiguration.setAllowedMethods(
                Stream.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH").collect(Collectors.toList())
        );
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new ApiKeyAuthenticationFilter(authenticationService),
                        UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(401);
                            response.getWriter().write("ApiKey validation failed");
                        })
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/error", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();
    }
}
