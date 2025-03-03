package com.hadid.swiftpay.controller;

import com.hadid.swiftpay.dto.request.UserAuthenticationRequest;
import com.hadid.swiftpay.dto.request.UserRegistrationRequest;
import com.hadid.swiftpay.dto.response.UserAuthenticationResponse;
import com.hadid.swiftpay.dto.response.UserRegistrationAndValidateResponse;
import com.hadid.swiftpay.service.AuthenticationService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<UserRegistrationAndValidateResponse> register(@RequestBody @Valid UserRegistrationRequest request) throws MessagingException {
        UserRegistrationAndValidateResponse response = authenticationService.register(request);
        return ResponseEntity.status(CREATED).body(response);
    }

    @GetMapping("/activate-account")
    public ResponseEntity<UserRegistrationAndValidateResponse> activateAccount(@RequestParam String token) throws MessagingException {
        UserRegistrationAndValidateResponse response = authenticationService.activateAccount(token);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<UserAuthenticationResponse> authenticate(@RequestBody @Valid UserAuthenticationRequest request) throws MessagingException {
        UserAuthenticationResponse response = authenticationService.authenticate(request);
        return ResponseEntity.ok(response);
    }

}
