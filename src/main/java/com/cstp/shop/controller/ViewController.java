package com.cstp.shop.controller;

import com.cstp.shop.model.dto.LoginDto;
import com.cstp.shop.model.dto.SignupDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ViewController
{
    @GetMapping("/login")
    public ModelAndView viewLogin()
    {
        ModelAndView mav = new ModelAndView("login");
        mav.addObject("signupDto", new SignupDto());
        mav.addObject("loginDto", new LoginDto());
        return mav;
    }
}
