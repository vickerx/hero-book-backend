package com.thoughtworks.herobook.auth.repository;

import com.thoughtworks.herobook.auth.entity.ActivationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActivationCodeRepository extends JpaRepository<ActivationCode, Long> {
    Optional<ActivationCode> findByActivationCode(String code);
}
