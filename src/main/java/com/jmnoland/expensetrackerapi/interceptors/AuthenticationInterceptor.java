package com.jmnoland.expensetrackerapi.interceptors;

import com.jmnoland.expensetrackerapi.helpers.ApiKeyHelper;
import com.jmnoland.expensetrackerapi.interfaces.services.AuthenticationServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

    private final AuthenticationServiceInterface authenticationService;

    @Autowired
    public AuthenticationInterceptor(AuthenticationServiceInterface authenticationService) {
        this.authenticationService = authenticationService;
    }

    private void send401Response(HttpServletResponse response) throws IOException {
        response.getWriter().write("ApiKey validation failed");
        response.setStatus(401);
    }

    @Override
    public boolean preHandle(HttpServletRequest requestServlet, HttpServletResponse responseServlet, Object handler) throws IOException {
        String uri = requestServlet.getRequestURI();

        if (uri.contains("/api/") && !uri.contains("client/create")) {
            String key = ApiKeyHelper.getBase64String(requestServlet);

            if (key.length() == 0) {
                send401Response(responseServlet);
                return false;
            }

            boolean isValid = this.authenticationService.validateApiKey(key);
            if (!isValid)
                send401Response(responseServlet);
            return isValid;
        }

        return true;
    }
}
