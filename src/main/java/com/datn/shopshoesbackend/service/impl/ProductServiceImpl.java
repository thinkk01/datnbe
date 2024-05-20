package com.datn.shopshoesbackend.service.impl;

import com.datn.shopshoesbackend.domain.constant.ProductConst;
import com.datn.shopshoesbackend.domain.dto.ResponseProductDto;
import com.datn.shopshoesbackend.repository.ProductRepository;
import com.datn.shopshoesbackend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;
    @Override
    public Page<ResponseProductDto> getProducts(Boolean active, Pageable pageable) {
        return productRepository.getAllProducts(ProductConst.PRODUCT_AVG_SIZE,ProductConst.PRODUCT_MAIN_IMAGE,active
        ,pageable);
    }
}
