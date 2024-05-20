package com.datn.shopshoesbackend.controller;

import com.datn.shopshoesbackend.domain.constant.ProductConst;
import com.datn.shopshoesbackend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@CrossOrigin("*")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping(ProductConst.API_PRODUCT_GET_ALL)
    public ResponseEntity<?> getAllProductPagination(@RequestParam("page") Optional<Integer> page,
                                                     @RequestParam("size") Optional<Integer> size,
                                                     @RequestParam("active") Optional<Boolean> active){
        Sort sort = Sort.by(Sort.Direction.DESC,"modifyDate");
        Pageable pageable = PageRequest.of(page.orElse(1) - 1,size.orElse(8),sort);
        return new ResponseEntity<>(productService.getProducts(active.orElse(true),pageable), HttpStatus.OK);
    }

}
