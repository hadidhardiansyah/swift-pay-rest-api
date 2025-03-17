package com.hadid.swiftpay.service;

import com.hadid.swiftpay.entity.User;
import com.hadid.swiftpay.entity.UserPrincipal;
import com.hadid.swiftpay.entity.Wallet;
import com.hadid.swiftpay.exception.BusinessException;
import com.hadid.swiftpay.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.hadid.swiftpay.exception.BusinessErrorCodes.WALLET_NOT_FOUND;

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

    public Wallet getWallet(Authentication connectedUser) {
        UserPrincipal userPrincipal = (UserPrincipal) connectedUser.getPrincipal();

        return walletRepository.findByUserId(userPrincipal.getUser().getId())
                .orElseThrow(() -> new BusinessException(WALLET_NOT_FOUND));
    }

}
