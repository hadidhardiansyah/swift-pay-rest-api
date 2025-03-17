package com.hadid.swiftpay.dto.response;

import com.hadid.swiftpay.enums.TransactionType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TransactionResponse {

    private String message;

    private String status;

    private TransactionType type;

}
