package com.hadid.swiftpay.mapper;

import com.hadid.swiftpay.dto.response.TransactionResponse;
import com.hadid.swiftpay.entity.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionMapper {

    private final WallerMapper wallerMapper;

    public TransactionResponse toTransactionResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .type(transaction.getType().toString())
                .status(transaction.getStatus().toString())
                .createdAt(transaction.getCreatedAt())
                .sourceWallet(transaction.getSourceWallet() != null ? wallerMapper.toWalletResponse(transaction.getSourceWallet()) : null)
                .destinationWallet(transaction.getDestinationWallet() != null ? wallerMapper.toWalletResponse(transaction.getDestinationWallet()) : null)
                .build();
    }

}
