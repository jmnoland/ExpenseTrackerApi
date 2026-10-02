package com.jmnoland.expensetrackerapi.security;

import com.jmnoland.expensetrackerapi.helpers.ApiKeyHelper;
import com.jmnoland.expensetrackerapi.helpers.RequestHelper;
import com.jmnoland.expensetrackerapi.interfaces.services.AuthenticationServiceInterface;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

public class ApiKeyAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationServiceInterface authenticationService;

    public ApiKeyAuthenticationFilter(AuthenticationServiceInterface authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String key = ApiKeyHelper.getBase64String(request);
        String clientId = RequestHelper.getClientIdFromHeader(request);

        if (key.length() > 0 && isValid(key, clientId)) {
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(
                    UsernamePasswordAuthenticationToken.authenticated(clientId, null, Collections.emptyList())
            );
            SecurityContextHolder.setContext(context);
        }

        // Unauthenticated requests to protected paths are rejected by the entry point in SecurityConfig
        filterChain.doFilter(request, response);
    }

    private boolean isValid(String key, String clientId) {
        try {
            return this.authenticationService.validateApiKey(key, clientId);
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            // Malformed base64 or missing ':' separator
            return false;
        }
    }
}
