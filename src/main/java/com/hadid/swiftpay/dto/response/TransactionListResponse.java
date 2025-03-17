package com.hadid.swiftpay.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class TransactionListResponse {

    List<TransactionResponse> sentTransactions;

    List<TransactionResponse> receivedTransactions;

}
