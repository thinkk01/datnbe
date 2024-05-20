package com.datn.shopshoesbackend.service.impl;

import com.datn.shopshoesbackend.dto.ResAccountDto;
import com.datn.shopshoesbackend.repository.AccountRepository;
import com.datn.shopshoesbackend.service.AccountService;
import com.datn.shopshoesbackend.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    RoleService roleService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public ResAccountDto findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }
}
