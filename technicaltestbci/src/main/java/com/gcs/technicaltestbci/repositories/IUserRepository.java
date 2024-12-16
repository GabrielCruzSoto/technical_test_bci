package com.gcs.technicaltestbci.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gcs.technicaltestbci.entities.UserEntity;

public interface IUserRepository extends JpaRepository<UserEntity,UUID>{
    boolean existsByEmail(String email);
    Optional<UserEntity> findByEmail(String email);
}
