package com.hadid.swiftpay.service;

import com.hadid.swiftpay.dto.request.TransferRequest;
import com.hadid.swiftpay.dto.response.TransactionResponse;
import com.hadid.swiftpay.entity.Transaction;
import com.hadid.swiftpay.entity.UserPrincipal;
import com.hadid.swiftpay.entity.Wallet;
import com.hadid.swiftpay.exception.BusinessException;
import com.hadid.swiftpay.repository.WalletRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.hadid.swiftpay.enums.TransactionStatus.FAILED;
import static com.hadid.swiftpay.enums.TransactionStatus.SUCCESS;
import static com.hadid.swiftpay.enums.TransactionType.TRANSFER;
import static com.hadid.swiftpay.exception.BusinessErrorCodes.*;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final WalletRepository walletRepository;

    private final TransactionService transactionService;

    @Transactional
    public TransactionResponse transfer(TransferRequest request, Authentication connectedUser) {
        UserPrincipal userPrincipal = (UserPrincipal) connectedUser.getPrincipal();
        Wallet sourceWallet = walletRepository.findByUserId(request.getSourceWalletId())
                .orElseThrow(() -> new BusinessException(SOURCE_WALLET_NOT_FOUND));

        if (!sourceWallet.getUser().getId().equals(userPrincipal.getUser().getId())) {
            throw new AccessDeniedException("Unauthorized to access this wallet");
        }

        Wallet destinationWallet = walletRepository.findByUserId(request.getDestinationWalletId())
                .orElseThrow(() -> new BusinessException(DESTINATION_WALLET_NOT_FOUND));

        BigDecimal amount = request.getAmount();

        if (sourceWallet.getBalance().compareTo(amount) <= 0) {
            throw new BusinessException(INSUFFICIENT_BALANCE);
        }

        Transaction newTransaction = transactionService.createTransaction(sourceWallet, destinationWallet, amount, TRANSFER);

        try {
            sourceWallet.setBalance(sourceWallet.getBalance().subtract(amount));
            destinationWallet.setBalance(destinationWallet.getBalance().add(amount));

            walletRepository.save(sourceWallet);
            walletRepository.save(destinationWallet);

            transactionService.updateTransactionStatus(newTransaction.getId(), SUCCESS);

            return TransactionResponse.builder()
                    .message("Transfer successful")
                    .status("success")
                    .type(TRANSFER)
                    .build();
        } catch (Exception e) {
            transactionService.updateTransactionStatus(newTransaction.getId(), FAILED);
            throw e;
        }
    }

}
