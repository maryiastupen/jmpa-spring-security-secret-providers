package com.epam.learnspringsecurity.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * Custom Authentication Handler
 */
@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private static final String USER_IS_BLOCKED = "User is blocked";
    private static final String BAD_CREDENTIALS = "Bad credentials";
    private static final String DEFAULT_FAILURE_URL = "/login?error";

    /**
     * Redirects to {@value #DEFAULT_FAILURE_URL}, passing an appropriate error message
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws ServletException, IOException {
        setDefaultFailureUrl(DEFAULT_FAILURE_URL);
        super.onAuthenticationFailure(request, response, authenticationException);
        String errorMessage = BAD_CREDENTIALS;
        if (authenticationException.getMessage().equalsIgnoreCase(USER_IS_BLOCKED)) {
            errorMessage = USER_IS_BLOCKED;
        }
        request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errorMessage);
    }

}