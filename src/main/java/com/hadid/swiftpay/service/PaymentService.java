package com.hadid.swiftpay.service;

import com.hadid.swiftpay.dto.request.PaymentRequest;
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
import static com.hadid.swiftpay.enums.TransactionType.PAYMENT;
import static com.hadid.swiftpay.exception.BusinessErrorCodes.INSUFFICIENT_BALANCE;
import static com.hadid.swiftpay.exception.BusinessErrorCodes.SOURCE_WALLET_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final WalletRepository walletRepository;

    private final TransactionService transactionService;

    @Transactional
    public TransactionResponse createPayment(PaymentRequest request, Authentication connectedUser) {
        UserPrincipal userPrincipal = (UserPrincipal) connectedUser.getPrincipal();
        Wallet sourceWallet = walletRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new BusinessException(SOURCE_WALLET_NOT_FOUND));

        if (!sourceWallet.getUser().getId().equals(userPrincipal.getUser().getId())) {
            throw new AccessDeniedException("Unauthorized to access this wallet");
        }

        BigDecimal amount = request.getAmount();

        if (sourceWallet.getBalance().compareTo(amount) < 0) {
            throw new BusinessException(INSUFFICIENT_BALANCE);
        }

        Transaction newTransaction = transactionService.createTransaction(sourceWallet, null, amount, PAYMENT);

        try {
            sourceWallet.setBalance(sourceWallet.getBalance().subtract(amount));

            walletRepository.save(sourceWallet);

            transactionService.updateTransactionStatus(newTransaction.getId(), SUCCESS);

            return TransactionResponse.builder()
                    .message("Payment successful")
                    .status("success")
                    .type(PAYMENT)
                    .build();
        } catch (Exception e) {
            transactionService.updateTransactionStatus(newTransaction.getId(), FAILED);
            throw e;
        }
    }

}
