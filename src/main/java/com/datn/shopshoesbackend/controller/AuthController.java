package com.datn.shopshoesbackend.controller;

import com.datn.shopshoesbackend.domain.constant.AccountConst;
import com.datn.shopshoesbackend.domain.constant.RoleConst;
import com.datn.shopshoesbackend.domain.payload.request.LoginRequest;
import com.datn.shopshoesbackend.domain.payload.response.LoginResponse;
import com.datn.shopshoesbackend.entity.Account;
import com.datn.shopshoesbackend.exception.AppException;
import com.datn.shopshoesbackend.jwt.JwtTokenProvider;
import com.datn.shopshoesbackend.repository.AccountRepository;
import com.datn.shopshoesbackend.security.CustomUserDetails;
import com.datn.shopshoesbackend.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenProvider tokenProvider;
    @Autowired
    AccountService accountService;
    @Autowired
    AccountRepository accountRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/api/site/login")
    public LoginResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Optional<Account> optionalAccount = Optional.ofNullable(accountRepository.findAccountByUsername(loginRequest.getUsername()));
            if (optionalAccount.isEmpty()) {
                throw new UsernameNotFoundException("Invalid username!");
            }
            Account account = optionalAccount.get();

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(), loginRequest.getPassword()
            );
            authenticationManager.authenticate(authenticationToken);
            CustomUserDetails customUserDetails = new CustomUserDetails(account);
            return new LoginResponse(jwtTokenProvider.generateToken(customUserDetails));
            //            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            loginRequest.getUsername(),
//                            loginRequest.getPassword()
//                    )
//            );
//            if(loginRequest.getAdmin()){
//                if(authentication.getAuthorities().toArray()[0].toString().equals(RoleConst.ROLE_CUSTOMER)){
//                    throw new AppException(AccountConst.ACCOUNT_MSG_ERROR_ACCESS_DENIED);
//                }
//            }
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());

//            return new LoginResponse(jwt);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(AccountConst.ACCOUNT_MSG_ERROR_SIGN_IN);
        }
    }

    @GetMapping(AccountConst.API_ACCOUNT_FIND_ME)
    public ResponseEntity<?> getUser(@RequestParam("token") String token) {
        if (tokenProvider.validateToken(token)) {
            String username = tokenProvider.getUserNameFromJwt(token);
            return new ResponseEntity<>(accountService.findByUsername(username), HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid token", HttpStatus.UNAUTHORIZED);
    }
}
