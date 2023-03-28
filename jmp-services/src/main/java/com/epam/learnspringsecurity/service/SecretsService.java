package com.epam.learnspringsecurity.service;

import java.util.Optional;

import com.epam.learnspringsecurity.entity.Secret;
import com.epam.learnspringsecurity.exception.NotFoundException;
import com.epam.learnspringsecurity.repository.SecretsRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.lang.String.format;

/**
 * Service, that works with secrets
 */
@Service
public class SecretsService {

    private final SecretsRepository secretsRepository;
    private final UniqueLinkAddressService uniqueLinkAddressService;

    public SecretsService(SecretsRepository secretsRepository, UniqueLinkAddressService uniqueLinkAddressService) {
        this.secretsRepository = secretsRepository;
        this.uniqueLinkAddressService = uniqueLinkAddressService;
    }

    /**
     * Gets secret by its unique link address identifier
     *
     * @param identifier unique link address identifier
     * @return secret
     */
    @Transactional(readOnly = true)
    public Secret getSecretValueByUniqueIdentifier(String identifier) {
        return secretsRepository.findSecretByUniqueAddressIdentifier(identifier)
                .orElseThrow(() -> new NotFoundException(format("Secret value with provided unique link identifier: %s cannot be found", identifier)));
    }

    /**
     * Removes secret
     *
     * @param secret secret entity to remove
     */
    @Transactional
    public void removeSecret(Secret secret) {
        secretsRepository.delete(secret);
    }

    /**
     * Creates secret based on the passed secret value
     *
     * @param optionalSecret optional secret value
     * @return created secret
     */
    @Transactional
    public Secret createSecret(Optional<String> optionalSecret) {
        return optionalSecret
                .map(String::trim)
                .filter(StringUtils::isNotEmpty)
                .map(value -> secretsRepository.save(new Secret(uniqueLinkAddressService.generateUniqueLinkAddressIdentifier(), value)))
                .orElseThrow(() -> new IllegalStateException("Can not create secret without value."));
    }

}
