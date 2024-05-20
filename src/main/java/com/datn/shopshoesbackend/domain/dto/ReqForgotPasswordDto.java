package com.datn.shopshoesbackend.domain.dto;

import lombok.Data;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
@Data
public class ReqForgotPasswordDto {
    @NotNull
    @NotEmpty
    private String username;
}
