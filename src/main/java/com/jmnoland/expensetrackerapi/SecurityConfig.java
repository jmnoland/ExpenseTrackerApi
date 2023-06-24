package com.jmnoland.expensetrackerapi;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
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
        http.csrf().disable().cors().configurationSource(request -> corsConfiguration);
    }
}
