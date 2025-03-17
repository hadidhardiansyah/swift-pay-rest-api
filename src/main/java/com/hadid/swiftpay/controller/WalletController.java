package com.hadid.swiftpay.controller;

import com.hadid.swiftpay.entity.Wallet;
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
    public ResponseEntity<Wallet> getWallet(Authentication connectedUser) {
        Wallet wallet = walletService.getWallet(connectedUser);
        return ResponseEntity.ok(wallet);
    }

}
