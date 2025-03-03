package com.hadid.swiftpay.repository;

import com.hadid.swiftpay.entity.Authentication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthenticationRepository extends JpaRepository<Authentication, Long> {

    Optional<Authentication> findByToken(String token);

}
