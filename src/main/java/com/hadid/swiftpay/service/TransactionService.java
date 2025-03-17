package com.hadid.swiftpay.service;

import com.hadid.swiftpay.entity.Transaction;
import com.hadid.swiftpay.entity.Wallet;
import com.hadid.swiftpay.enums.TransactionStatus;
import com.hadid.swiftpay.enums.TransactionType;
import com.hadid.swiftpay.exception.BusinessException;
import com.hadid.swiftpay.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.hadid.swiftpay.enums.TransactionStatus.PENDING;
import static com.hadid.swiftpay.exception.BusinessErrorCodes.DESTINATION_WALLET_REQUIRED;
import static com.hadid.swiftpay.exception.BusinessErrorCodes.TRANSACTION_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    @Transactional
    public Transaction createTransaction(Wallet sourceWallet, Wallet destinationWallet, BigDecimal amount, TransactionType type) {
        if (destinationWallet == null && type == TransactionType.TRANSFER) {
            throw new BusinessException(DESTINATION_WALLET_REQUIRED);
        }

        Transaction transaction = Transaction.builder()
                .sourceWallet(sourceWallet)
                .destinationWallet(destinationWallet)
                .amount(amount)
                .type(type)
                .status(PENDING)
                .build();

        return transactionRepository.save(transaction);
    }

    @Transactional
    public void updateTransactionStatus(Long transactionId, TransactionStatus status) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new BusinessException(TRANSACTION_NOT_FOUND));

        transaction.setStatus(status);
        transactionRepository.save(transaction);
    }

}
