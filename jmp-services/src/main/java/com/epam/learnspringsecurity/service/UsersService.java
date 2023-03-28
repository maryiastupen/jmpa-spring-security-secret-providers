package com.epam.learnspringsecurity.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.epam.learnspringsecurity.entity.UserEntity;
import com.epam.learnspringsecurity.repository.UserEntityRepository;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

/**
 * Implementation of {@link UserDetailsService}
 */
@Service
public class UsersService implements UserDetailsService {

    public static final String DEFAULT_AUTHORITIES_SEPARATOR = ";";
    private final UserEntityRepository userEntityRepository;
    private final LoginAttemptService loginAttemptService;

    public UsersService(UserEntityRepository userEntityRepository, LoginAttemptService loginAttemptService) {
        this.userEntityRepository = userEntityRepository;
        this.loginAttemptService = loginAttemptService;
    }

    /**
     * Loads user's details based on the passed username
     *
     * @param username the username identifying the user whose data is required.
     * @return {@link UserDetails} for the found user
     * @throws UsernameNotFoundException in case user was not found in repository
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userEntityRepository.findByUserName(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException(format("User with username %s was not found", username));
        } else {
            if (loginAttemptService.shouldBeBlocked(username)) {
                throw new LockedException("User is blocked");
            }
        }

        String[] authorities = userEntity.getUserAuthorities().split(DEFAULT_AUTHORITIES_SEPARATOR);

        return User.withUsername(userEntity.getUserName())
                .password(userEntity.getUserPassword())
                .authorities(authorities)
                .build();
    }

    /**
     * Gets usernames of blocked users along with the timestamps when they were blocked
     *
     * @return map of username - blocked timestamp
     */
    public Map<String, LocalDateTime> getBlockedUsers() {
        List<UserEntity> users = userEntityRepository.findAll();
        return users.stream().map(UserEntity::getUserName)
                .filter(loginAttemptService::shouldBeBlocked)
                .collect(Collectors.toMap(user -> user, user -> loginAttemptService.getCachedValue(user).getBlockedTimestamp()));
    }

}
