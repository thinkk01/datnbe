package com.datn.shopshoesbackend.service;

import com.datn.shopshoesbackend.dto.ResAccountDto;


public interface AccountService {
    ResAccountDto findByUsername(String username);
}
