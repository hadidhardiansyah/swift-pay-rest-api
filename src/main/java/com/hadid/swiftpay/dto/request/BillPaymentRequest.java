package com.hadid.swiftpay.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class BillPaymentRequest {

    private Long userId;

    private BigDecimal amount;

    @NotEmpty(message = "Bill number is mandatory")
    @NotBlank(message = "Bill number is mandatory")
    private String billNumber;

}
