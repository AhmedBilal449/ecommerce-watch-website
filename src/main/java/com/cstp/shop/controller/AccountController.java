package com.cstp.shop.controller;

import com.cstp.shop.model.Account;
import com.cstp.shop.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    AccountRepository accountRepository;

    /*
        Right now this is semi functional, it creates an account with the correct email
        However getting any other parameter returns null
     */
    @PostMapping("/create-account")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        try {
            System.out.println("ResponseEntity: "+ account.getEmail()+" "+ account.getPassHash()+" "+account.getGroup() );
            Account _account = accountRepository
                    .save(new Account(account.getEmail(), account.getPassHash(), "CUSTOMER"));
            return new ResponseEntity<>(_account, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
