package com.hadid.swiftpay.service;

import com.hadid.swiftpay.dto.request.TopUpRequest;
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
import static com.hadid.swiftpay.enums.TransactionType.TOP_UP;
import static com.hadid.swiftpay.exception.BusinessErrorCodes.*;

@Service
@RequiredArgsConstructor
public class TopUpService {

    private final WalletRepository walletRepository;

    private final TransactionService transactionService;

    @Transactional
    public TransactionResponse topUp(TopUpRequest request, Authentication connectedUser) {
        UserPrincipal userPrincipal = (UserPrincipal) connectedUser.getPrincipal();
        Wallet destinationWallet = walletRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new BusinessException(DESTINATION_WALLET_NOT_FOUND));

        if (!destinationWallet.getUser().getId().equals(userPrincipal.getUser().getId())) {
            throw new AccessDeniedException("Unauthorized to access this wallet");
        }

        BigDecimal amount = request.getAmount();

        Transaction newTransaction = transactionService.createTransaction(null, destinationWallet, amount, TOP_UP);

        try {
            destinationWallet.setBalance(destinationWallet.getBalance().add(amount));

            walletRepository.save(destinationWallet);

            transactionService.updateTransactionStatus(newTransaction.getId(), SUCCESS);

            return TransactionResponse.builder()
                    .message("Top up successful")
                    .status("success")
                    .type(TOP_UP)
                    .build();
        } catch (Exception e) {
            transactionService.updateTransactionStatus(newTransaction.getId(), FAILED);
            throw e;
        }
    }

}
