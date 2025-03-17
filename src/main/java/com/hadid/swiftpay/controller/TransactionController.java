package com.hadid.swiftpay.controller;

import com.hadid.swiftpay.dto.request.BillPaymentRequest;
import com.hadid.swiftpay.dto.request.PaymentRequest;
import com.hadid.swiftpay.dto.request.TopUpRequest;
import com.hadid.swiftpay.dto.request.TransferRequest;
import com.hadid.swiftpay.dto.response.ActionTransactionResponse;
import com.hadid.swiftpay.service.BillPaymentService;
import com.hadid.swiftpay.service.PaymentService;
import com.hadid.swiftpay.service.TopUpService;
import com.hadid.swiftpay.service.TransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final BillPaymentService billPaymentService;

    private final PaymentService paymentService;

    private final TopUpService topUpService;

    private final TransferService transferService;

    @PostMapping("/transfer")
    public ResponseEntity<ActionTransactionResponse> transfer(@RequestBody TransferRequest request, Authentication connectedUser) {
        ActionTransactionResponse response = transferService.transfer(request, connectedUser);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/payment")
    public ResponseEntity<ActionTransactionResponse> payment(@RequestBody PaymentRequest request, Authentication connectedUser) {
        ActionTransactionResponse response = paymentService.createPayment(request, connectedUser);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/bill-payment")
    public ResponseEntity<ActionTransactionResponse> payBill(@Valid @RequestBody BillPaymentRequest request, Authentication connectedUser) {
        ActionTransactionResponse response = billPaymentService.payBill(request, connectedUser);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/topup")
    public ResponseEntity<ActionTransactionResponse> topUp(@RequestBody TopUpRequest request, Authentication connectedUser) {
        ActionTransactionResponse response = topUpService.topUp(request, connectedUser);
        return ResponseEntity.ok(response);
    }

}
