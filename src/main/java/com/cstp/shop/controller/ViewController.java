package com.cstp.shop.controller;

import com.cstp.shop.model.dto.LoginDto;
import com.cstp.shop.model.dto.SignupDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
public class ViewController
{
    @GetMapping({"/login", "/"})
    public ModelAndView viewLogin()
    {
        ModelAndView mav = new ModelAndView("login");
        mav.addObject("signupDto", new SignupDto());
        mav.addObject("loginDto", new LoginDto());
        return mav;
    }

    @GetMapping("/products")
    public ModelAndView viewProducts()
    {
        ModelAndView mav = new ModelAndView("product");
        return mav;
    }

    @GetMapping("/audemarspiguet")
    public ModelAndView viewAudemarspiguet() {
        ModelAndView mav = new ModelAndView("audemarspiguet");
        return mav;
    }

    @GetMapping("/rolex")
    public ModelAndView viewRolex() {
        ModelAndView mav = new ModelAndView("rolex");
        return mav;
    }

    @GetMapping("/cartier")
    public ModelAndView viewCartier() {
        ModelAndView mav = new ModelAndView("cartier");
        return mav;
    }

    @GetMapping("/armani")
    public ModelAndView viewArmani() {
        ModelAndView mav = new ModelAndView("armani");
        return mav;
    }

    @GetMapping("/patekphilippe")
    public ModelAndView viewPatekPhilippe() {
        ModelAndView mav = new ModelAndView("patekphilippe");
        return mav;
    }

}
