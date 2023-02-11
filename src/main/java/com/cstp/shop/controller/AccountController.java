package com.cstp.shop.controller;

import com.cstp.shop.model.Account;
import com.cstp.shop.model.dto.AccountDto;
import com.cstp.shop.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class AccountController {

    @Autowired
    AccountRepository accountRepository;

    @GetMapping({"/login", "/"})
    public ModelAndView viewLogin() {
        ModelAndView mav = new ModelAndView("login");
        return mav;
    }

    @PostMapping("/create-account")
    public ResponseEntity<Account> createAccount(@RequestBody AccountDto accountDto) {

        try {
            Account _account = accountRepository.save(accountDto.toAccount());
            return new ResponseEntity<>(_account, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
