package com.hadid.swiftpay.service;

import com.hadid.swiftpay.dto.request.UserRegistrationRequest;
import com.hadid.swiftpay.dto.response.UserRegistrationAndValidateResponse;
import com.hadid.swiftpay.entity.Authentication;
import com.hadid.swiftpay.entity.User;
import com.hadid.swiftpay.enums.EmailTemplateName;
import com.hadid.swiftpay.exception.BusinessException;
import com.hadid.swiftpay.repository.AuthenticationRepository;
import com.hadid.swiftpay.repository.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

import static com.hadid.swiftpay.exception.BusinessErrorCodes.*;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final AuthenticationRepository authenticationRepository;

    private final EmailService emailService;

    private final PasswordEncoder passwordEncoder;

    public UserRegistrationAndValidateResponse register(UserRegistrationRequest request) throws MessagingException {
        validateUserUniqueness(request);

        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(false)
                .build();

        userRepository.save(user);
        sendValidationEmail(user);

        return UserRegistrationAndValidateResponse.builder()
                .message("User successfully registered")
                .status("success")
                .build();
    }

    private void validateUserUniqueness(UserRegistrationRequest request) throws BusinessException {
        if (userRepository.existsByUsernameOrEmail(request.getUsername(), request.getEmail())) {
            throw new BusinessException(USERNAME_OR_EMAIL_ALREADY_EXISTS);
        }
    }

    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);
        final String activationUrl = "http://localhost:4200/activate-account";

        emailService.sendEmail(
                user.getEmail(),
                user.fullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account Activation"
        );
    }

    private String generateAndSaveActivationToken(User user) {
//  Generate token
        String generateToken = generateActivationCode(6);
        var token = Authentication.builder()
                .token(generateToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();

        authenticationRepository.save(token);

        return generateToken;
    }

    private String generateActivationCode(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }

        return codeBuilder.toString();
    }

    public UserRegistrationAndValidateResponse activateAccount(String token) throws MessagingException {
        Authentication savedToken = authenticationRepository.findByToken(token)
                .orElseThrow(() -> new BusinessException(INVALID_ACTIVATION_CODE));
        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            sendValidationEmail(savedToken.getUser());

            throw new BusinessException(EXPIRED_ACTIVATION_CODE);
        }

        var user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new BusinessException(USER_NOT_FOUND));

        if (user.isEnabled()) {
            throw new BusinessException(ACCOUNT_ALREADY_ACTIVATED);
        }
        user.setEnabled(true);
        userRepository.save(user);
        savedToken.setValidatedAt(LocalDateTime.now());
        authenticationRepository.save(savedToken);

        return UserRegistrationAndValidateResponse.builder()
                .message("User successfully activated")
                .status("success")
                .build();
    }

}
