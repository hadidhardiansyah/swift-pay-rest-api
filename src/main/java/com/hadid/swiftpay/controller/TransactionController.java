package com.hadid.swiftpay.controller;

import com.hadid.swiftpay.dto.request.PaymentRequest;
import com.hadid.swiftpay.dto.request.TransferRequest;
import com.hadid.swiftpay.dto.response.TransactionResponse;
import com.hadid.swiftpay.service.PaymentService;
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

    private final TransferService transferService;

    private final PaymentService paymentService;

    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponse> transfer(@RequestBody TransferRequest request, Authentication connectedUser) {
        TransactionResponse response = transferService.transfer(request, connectedUser);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/payment")
    public ResponseEntity<TransactionResponse> payment(@RequestBody PaymentRequest request, Authentication connectedUser) {
        TransactionResponse response = paymentService.createPayment(request, connectedUser);
        return ResponseEntity.ok(response);
    }

}
