package com.datn.shopshoesbackend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AmountMonth {
    private Integer month;
    private Long count;
    private Double total;
}
