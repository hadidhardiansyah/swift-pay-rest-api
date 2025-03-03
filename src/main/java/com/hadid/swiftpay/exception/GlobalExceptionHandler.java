package com.hadid.swiftpay.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ExceptionResponse> handleBusinessException(BusinessException exception) {
        BusinessErrorCodes errorCode = exception.getErrorCode();

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(errorCode.getCode())
                                .businessErrorDescription(errorCode.getDescription())
                                .build()
                );
    }

}
