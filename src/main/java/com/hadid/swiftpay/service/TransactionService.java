package com.hadid.swiftpay.service;

import com.hadid.swiftpay.dto.response.TransactionListResponse;
import com.hadid.swiftpay.dto.response.TransactionResponse;
import com.hadid.swiftpay.entity.Transaction;
import com.hadid.swiftpay.entity.UserPrincipal;
import com.hadid.swiftpay.entity.Wallet;
import com.hadid.swiftpay.enums.TransactionStatus;
import com.hadid.swiftpay.enums.TransactionType;
import com.hadid.swiftpay.exception.BusinessException;
import com.hadid.swiftpay.mapper.TransactionMapper;
import com.hadid.swiftpay.repository.TransactionRepository;
import com.hadid.swiftpay.repository.WalletRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.hadid.swiftpay.enums.TransactionStatus.PENDING;
import static com.hadid.swiftpay.exception.BusinessErrorCodes.*;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionMapper transactionMapper;

    private final TransactionRepository transactionRepository;

    private final WalletRepository walletRepository;

    public TransactionListResponse getAllTransactions(Authentication connectedUser) {
        UserPrincipal userPrincipal = (UserPrincipal) connectedUser.getPrincipal();
        Wallet wallet = walletRepository.findByUserId(userPrincipal.getUser().getId())
                .orElseThrow(() -> new BusinessException(WALLET_NOT_FOUND));

        List<TransactionResponse> sentTransactions = transactionRepository.findBySourceWalletId(wallet.getId())
                .stream()
                .map(transactionMapper::toTransactionResponse)
                .toList();

        List<TransactionResponse> receivedTransactions = transactionRepository.findByDestinationWalletId(wallet.getId())
                .stream()
                .map(transactionMapper::toTransactionResponse)
                .toList();

        return TransactionListResponse.builder()
                .sentTransactions(sentTransactions)
                .receivedTransactions(receivedTransactions)
                .build();
    }

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
