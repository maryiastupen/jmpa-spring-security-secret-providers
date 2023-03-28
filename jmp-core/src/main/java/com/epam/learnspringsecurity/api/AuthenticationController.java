package com.epam.learnspringsecurity.api;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller to manage the authentication endpoints
 */
@Controller
public class AuthenticationController {

    @GetMapping("/login")
    public String getLoginPage(final ModelMap model, @RequestParam("error") final Optional<String> error) {
        error.ifPresent(e -> model.addAttribute("error", e));
        return "login";
    }

    @GetMapping("/logoutSuccess")
    public String getLogoutSuccessPage() {
        return "logout";
    }

}
