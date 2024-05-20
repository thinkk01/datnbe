package com.datn.shopshoesbackend.domain.payload.response;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    public LoginResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
