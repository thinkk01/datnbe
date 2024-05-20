package com.datn.shopshoesbackend.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqUpdateOrderDto {
    @NotEmpty(message = "Không để trống địa chỉ.")
    private String address;
    @NotEmpty(message = "Không để trống email.")
    private String email;
    @NotEmpty(message = "Không để trống họ tên.")
    private String fullname;
    @NotNull(message = "Không để trống trạng thái thanh toán.")
    private Boolean isPending;
    private String note;
    @NotNull(message = "Không để trống mã đơn hàng.")
    private Long orderId;
    @NotEmpty(message = "Không để trống số điện thoại.")
    private String phone;
}
