package com.epam.learnspringsecurity.api;

import java.net.URISyntaxException;
import java.util.Optional;

import com.epam.learnspringsecurity.entity.Secret;
import com.epam.learnspringsecurity.exception.NotFoundException;
import com.epam.learnspringsecurity.service.SecretsService;
import com.epam.learnspringsecurity.service.UniqueLinkAddressService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller to manage secrets
 */
@Controller
@RequestMapping(value = SecretsController.SECRETS_BASE_URL)
public class SecretsController {

    public static final String SECRETS_BASE_URL = "/secrets";

    private final SecretsService secretsService;
    private final UniqueLinkAddressService uniqueLinkAddressService;

    public SecretsController(SecretsService secretsService, UniqueLinkAddressService uniqueLinkAddressService) {
        this.secretsService = secretsService;
        this.uniqueLinkAddressService = uniqueLinkAddressService;
    }

    @GetMapping()
    public String getSecretFormPage() {
        return "secret-generate";
    }

    @PostMapping()
    public String generateLinkToSecret(final Model model, @RequestParam("secret") final Optional<String> secretValue) {
        try {
            Secret secret = secretsService.createSecret(secretValue);
            String uniqueAddress = uniqueLinkAddressService.getAddressWithUniqueLinkAddressIdentifier(secret.getUniqueAddressIdentifier());
            model.addAttribute("uniqueLinkAddress", uniqueAddress);
        } catch (IllegalStateException | URISyntaxException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "secret-link";
    }

    @GetMapping("/{id}")
    public String obtainGeneratedSecretValue(final Model model, @PathVariable String id) {
        try {
            Secret secret = secretsService.getSecretValueByUniqueIdentifier(id);
            model.addAttribute("secret", secret.getSecretValue());
            secretsService.removeSecret(secret);
        } catch (NotFoundException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "secret-obtain";
    }

}
