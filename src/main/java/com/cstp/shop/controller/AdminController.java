package com.cstp.shop.controller;

import com.cstp.shop.model.Product;
import com.cstp.shop.model.User;
import com.cstp.shop.model.dto.CartDto;
import com.cstp.shop.model.dto.LoginDto;
import com.cstp.shop.model.dto.SignupDto;
import com.cstp.shop.repository.ProductRepository;
import com.cstp.shop.repository.UserRepository;
import com.cstp.shop.service.CartService;
import com.cstp.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;


@RequestMapping("/admin")
@Controller
public class AdminController
{

    @Autowired
    UserRepository userRepository;
    @GetMapping({"/users"})
    public ModelAndView viewHome()
    {
        ModelAndView mav = new ModelAndView("manage_users");
        mav.addObject("users", userRepository.findAll());
        return mav;
    }


}
