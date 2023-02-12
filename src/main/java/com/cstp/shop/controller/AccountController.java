package com.cstp.shop.controller;

import com.cstp.shop.model.Account;
import com.cstp.shop.model.dto.AccountDto;
import com.cstp.shop.repository.AccountRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.util.MultiValueMap;

import java.util.Optional;

@RestController
public class AccountController {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    /*
        Redirects client to specific View
        Important to add 'accountDto' attribute, so it can handle forms
     */
    @GetMapping({"/login", "/"})
    public ModelAndView viewLogin() {
        ModelAndView mav = new ModelAndView("login");
        mav.addObject("accountDto", new AccountDto());
        return mav;
    }

    /*
        Processes form data request via URL "/logins/save"
        Used for registering an account
        Password is hashed before being saved to table
     */
    @PostMapping("/login/save")
    public String register(@Valid @ModelAttribute AccountDto account, BindingResult result, Model model)
    {
        boolean duplicate = accountRepository.existsByEmail(account.getEmail());

        if (duplicate) return "duplicate";
        if (!account.isValid()) return "invalid";

        account.setPassword( bCryptPasswordEncoder.encode(account.getPassword()) );
        accountRepository.save(account.toAccount());

        return "Form processed";
    }

}
