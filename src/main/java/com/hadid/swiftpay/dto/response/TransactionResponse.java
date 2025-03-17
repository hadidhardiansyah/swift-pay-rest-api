package com.hadid.swiftpay.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class TransactionResponse {

    private Long id;

    private BigDecimal amount;

    private String type;

    private String status;

    private LocalDateTime createdAt;

    private WalletResponse sourceWallet;

    private WalletResponse destinationWallet;

}
