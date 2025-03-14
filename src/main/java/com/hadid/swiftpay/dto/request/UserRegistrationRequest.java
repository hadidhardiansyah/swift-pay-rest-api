package com.hadid.swiftpay.dto.request;

import com.hadid.swiftpay.common.validator.ValidPassword;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserRegistrationRequest {

    @NotEmpty(message = "Firstname is mandatory")
    @NotBlank(message = "Firstname is mandatory")
    private String firstName;

    @NotEmpty(message = "Lastname is mandatory")
    @NotBlank(message = "Lastname is mandatory")
    private String lastName;

    @Pattern(regexp = "^[0-9]+$", message = "Phone number must contain only digits")
    @Size(min = 10, max = 13, message = "Phone number must be between 10 and 13 digits")
    @NotEmpty(message = "Phone Number is mandatory")
    @NotBlank(message = "Phone Number is mandatory")
    private String phoneNumber;

    @NotEmpty(message = "Username is mandatory")
    @NotBlank(message = "Username is mandatory")
    private String username;

    @Email(message = "Email is not formatted")
    @NotEmpty(message = "Email is mandatory")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotEmpty(message = "Password is mandatory")
    @NotBlank(message = "Password is mandatory")
    @ValidPassword
    private String password;

}
