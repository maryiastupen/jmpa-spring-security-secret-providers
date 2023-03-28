package com.epam.learnspringsecurity.listener;

import com.epam.learnspringsecurity.repository.UserEntityRepository;
import com.epam.learnspringsecurity.service.LoginAttemptService;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

/**
 * Custom Authentication Failure Listener
 */
@Component
public class CustomAuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    private final LoginAttemptService loginAttemptService;

    private final UserEntityRepository userEntityRepository;

    public CustomAuthenticationFailureListener(LoginAttemptService loginAttemptService, UserEntityRepository userEntityRepository) {
        this.loginAttemptService = loginAttemptService;
        this.userEntityRepository = userEntityRepository;
    }

    /**
     * Stores information about user's failed login in case {@link AuthenticationFailureBadCredentialsEvent} is received
     */
    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent e) {
        Object principal = e.getAuthentication().getPrincipal();
        if (principal instanceof String) {
            String username = (String) e.getAuthentication().getPrincipal();
            if (userEntityRepository.findByUserName(username) != null) {
                loginAttemptService.loginFailed(username);
            }
        }
    }

}
