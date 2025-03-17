package com.hadid.swiftpay.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class PaymentRequest {

    private Long userId;

    private BigDecimal amount;

}
