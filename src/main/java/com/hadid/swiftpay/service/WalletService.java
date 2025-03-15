package com.hadid.swiftpay.service;

import com.hadid.swiftpay.entity.User;
import com.hadid.swiftpay.entity.Wallet;
import com.hadid.swiftpay.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;

    public void createWallet(User user) {
        Wallet wallet = Wallet.builder()
                .user(user)
                .balance(BigDecimal.valueOf(0))
                .build();

        walletRepository.save(wallet);
    }

}
