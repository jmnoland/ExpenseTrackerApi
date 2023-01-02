package com.jmnoland.expensetrackerapi.interceptors;

import com.jmnoland.expensetrackerapi.interfaces.services.AuthenticationServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

    private final AuthenticationServiceInterface authenticationService;

    @Autowired
    public AuthenticationInterceptor(AuthenticationServiceInterface authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public boolean preHandle(HttpServletRequest requestServlet, HttpServletResponse responseServlet, Object handler)
    {
        String uri = requestServlet.getRequestURI();

        if (uri.contains("/api/") && !uri.contains("client/create")) {
            String header = requestServlet.getHeader("Authorization").substring(6);
            return this.authenticationService.validateApiKey(header);
        }

        return true;
    }
}
