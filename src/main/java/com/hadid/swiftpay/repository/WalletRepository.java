package com.hadid.swiftpay.repository;

import com.hadid.swiftpay.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> { }
