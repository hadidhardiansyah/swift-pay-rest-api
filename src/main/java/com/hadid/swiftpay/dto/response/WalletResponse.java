package com.hadid.swiftpay.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class WalletResponse {

    private Long id;

    private UserResponse user;

    private BigDecimal balance;

    private LocalDateTime createdAt;

}
