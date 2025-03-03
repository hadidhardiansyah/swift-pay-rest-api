package com.hadid.swiftpay.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserAuthenticationResponse {

    private String token;

    private String status;

}
