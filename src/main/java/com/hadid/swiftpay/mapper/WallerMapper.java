package com.hadid.swiftpay.mapper;

import com.hadid.swiftpay.dto.response.UserResponse;
import com.hadid.swiftpay.dto.response.WalletResponse;
import com.hadid.swiftpay.entity.Wallet;
import org.springframework.stereotype.Service;

@Service
public class WallerMapper {

    public WalletResponse toWalletResponse(Wallet wallet) {
        return WalletResponse.builder()
                .id(wallet.getId())
                .user(UserResponse.builder()
                        .id(wallet.getUser().getId())
                        .firstName(wallet.getUser().getFirstName())
                        .lastName(wallet.getUser().getLastName())
                        .phoneNumber(wallet.getUser().getPhoneNumber())
                        .username(wallet.getUser().getUsername())
                        .email(wallet.getUser().getEmail())
                        .build())
                .balance(wallet.getBalance())
                .createdAt(wallet.getCreatedAt())
                .build();
    }

}
