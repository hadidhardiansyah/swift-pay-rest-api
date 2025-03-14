package com.hadid.swiftpay.dto.request;

import com.hadid.swiftpay.common.validator.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserAuthenticationRequest {

    @NotEmpty(message = "Username or Email is mandatory")
    @NotBlank(message = "Username or Email is mandatory")
    private String usernameOrEmail;

    @NotEmpty(message = "Password is mandatory")
    @NotBlank(message = "Password is mandatory")
    @ValidPassword
    private String password;

}
