package com.hadid.swiftpay.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum BusinessErrorCodes {

    INVALID_ACTIVATION_CODE(400, BAD_REQUEST, "The activation code is invalid"),
    INVALID_CREDENTIALS(401, HttpStatus.UNAUTHORIZED, "Invalid username or password"),
    ACCOUNT_NOT_ACTIVATED(403, FORBIDDEN, "Your account is not activated"),
    USER_NOT_FOUND(404, NOT_FOUND ,"User not found"),
    ACCOUNT_ALREADY_ACTIVATED(405, METHOD_NOT_ALLOWED, "Your account is already activated"),
    USERNAME_OR_EMAIL_ALREADY_EXISTS(409, CONFLICT, "Username or email is already registered"),
    EXPIRED_ACTIVATION_CODE(410, GONE, "Activation token has expired. A new token has been sent to the same email address");

    private final int code;

    private final String description;

    private final HttpStatus httpStatus;

    BusinessErrorCodes(int code, HttpStatus httpStatus, String description) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.description = description;
    }

}
