package com.hadid.swiftpay.repository;

import com.hadid.swiftpay.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findBySourceWalletId(Long walletId);

    List<Transaction> findByDestinationWalletId(Long walletId);

}
