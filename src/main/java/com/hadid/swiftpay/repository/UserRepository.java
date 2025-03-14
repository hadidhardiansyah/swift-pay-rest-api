package com.hadid.swiftpay.repository;

import com.hadid.swiftpay.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

//    Optional<User> findByUsernameOrEmailOrPhoneNumber(String username, String email, String phoneNumber);
    boolean existsByUsernameOrEmailOrPhoneNumber(String username, String email, String phoneNumber);

    @Query("SELECT u FROM User u WHERE u.username = :identifier OR u.email = :identifier OR u.phoneNumber = :identifier")
    Optional<User> findByIdentifier(@Param("identifier") String identifier);

}
