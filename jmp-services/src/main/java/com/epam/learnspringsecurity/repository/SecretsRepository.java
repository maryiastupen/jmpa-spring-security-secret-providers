package com.epam.learnspringsecurity.repository;

import java.util.Optional;

import com.epam.learnspringsecurity.entity.Secret;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JpaRepository for interaction with {@link Secret}
 */
@Repository
public interface SecretsRepository extends JpaRepository<Secret, Long> {

    Optional<Secret> findSecretByUniqueAddressIdentifier(String uniqueAddressIdentifier);

}
