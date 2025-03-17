package com.hadid.swiftpay.controller;

import com.hadid.swiftpay.dto.response.WalletResponse;
import com.hadid.swiftpay.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/wallet")
public class WalletController {

    private final WalletService walletService;

    @GetMapping
    public ResponseEntity<WalletResponse> getWallet(Authentication connectedUser) {
        WalletResponse response = walletService.getWallet(connectedUser);
        return ResponseEntity.ok(response);
    }

}
