package com.datn.shopshoesbackend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReqAttributeDto {
    private Integer size;
    private Double price;
    private Integer stock;
}
