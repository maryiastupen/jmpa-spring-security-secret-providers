package com.epam.learnspringsecurity.repository;

import com.epam.learnspringsecurity.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JpaRepository for interaction with {@link UserEntity}
 */
@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUserName(String username);

}
