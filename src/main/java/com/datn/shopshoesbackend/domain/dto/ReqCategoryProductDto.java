package com.datn.shopshoesbackend.domain.dto;

import com.datn.shopshoesbackend.entity.Category;
import com.datn.shopshoesbackend.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ReqCategoryProductDto {

    private Long id;

    private Category category;

    private Product product;
}
