package com.datn.shopshoesbackend.domain.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Data
public class ReqChangePasswordDto {
    @NotNull
    @NotEmpty
    private String username;
    @NotNull
    @NotEmpty
    private String password;
    @NotNull
    @NotEmpty
    @Min(6)
    @Max(15)
    private String newPassword;
    @NotNull
    @NotEmpty
    @Min(6)
    @Max(15)
    private String newPasswordSecond;
}
