package com.datn.shopshoesbackend.service;

import com.datn.shopshoesbackend.domain.dto.ResponseProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Page<ResponseProductDto> getProducts (Boolean active, Pageable pageable);
}
