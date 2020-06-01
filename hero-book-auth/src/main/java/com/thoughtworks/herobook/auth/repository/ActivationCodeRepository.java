package com.thoughtworks.herobook.auth.repository;

import com.thoughtworks.herobook.auth.entity.ActivationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivationCodeRepository extends JpaRepository<ActivationCode, Long> {
}
