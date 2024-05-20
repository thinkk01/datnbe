package com.datn.shopshoesbackend.security;

import com.datn.shopshoesbackend.domain.constant.AccountConst;
import com.datn.shopshoesbackend.entity.Account;
import com.datn.shopshoesbackend.exception.AppException;
import com.datn.shopshoesbackend.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findAccountByUsername(username);
        if(account == null){
            throw new UsernameNotFoundException(username);
        }
        if(!account.getIsActive()){
            throw new AppException(AccountConst.ACCOUNT_MSG_ERROR_LOCK);
        }
        return new CustomUserDetails(account);
    }
}
