package com.epam.learnspringsecurity.config;

import java.util.HashMap;
import java.util.Map;

import com.epam.learnspringsecurity.handler.CustomAuthenticationFailureHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Web Security Configuration
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    /**
     * @return configured {@link SecurityFilterChain}
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, @Qualifier("daoAuthenticationProvider") AuthenticationProvider authenticationProvider, CustomAuthenticationFailureHandler authenticationFailureHandler) throws Exception {
        return http.authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .antMatchers(HttpMethod.GET, "/login*").permitAll()
                                .antMatchers(HttpMethod.GET, "/secrets*").hasAuthority("STANDARD")
                                .antMatchers(HttpMethod.POST, "/secrets*").hasAuthority("STANDARD")
                                .anyRequest().authenticated())
                .authenticationProvider(authenticationProvider)
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .failureHandler(authenticationFailureHandler)
                        .permitAll()
                )
                .logout(formLogout -> formLogout
                        .deleteCookies("JSESSIONID")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/logoutSuccess")
                        .permitAll()
                )
                .build();
    }

    /**
     * @return {@link AuthenticationProvider} with configured {@link UserDetailsService} and {@link PasswordEncoder}
     */
    @Bean("daoAuthenticationProvider")
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * @return {@link DelegatingPasswordEncoder}
     */
    @Bean
    public DelegatingPasswordEncoder passwordEncoder() {
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("bcrypt", new BCryptPasswordEncoder(12));
        encoders.put("noop", NoOpPasswordEncoder.getInstance());

        return new DelegatingPasswordEncoder("bcrypt", encoders);
    }

}
