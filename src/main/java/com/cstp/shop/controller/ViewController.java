package com.cstp.shop.controller;

import com.cstp.shop.model.Product;
import com.cstp.shop.model.dto.CartDto;
import com.cstp.shop.model.dto.LoginDto;
import com.cstp.shop.model.dto.SignupDto;
import com.cstp.shop.repository.ProductRepository;
import com.cstp.shop.service.CartService;
import com.cstp.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;


@RestController
public class ViewController
{
    private final CartService cartService;
    private final ProductService productService;

    @Autowired
    public ViewController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @Autowired
    ProductRepository productRepository;

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
        mav.addObject( "products", productRepository.findAll() );
        mav.addObject( "cartDto", new CartDto() );
        return mav;
    }

    @GetMapping({"/basket", "/cart"})
    public ModelAndView viewBasket()
    {
        ModelAndView mav = new ModelAndView("basket");
        mav.addObject( "cartItems", cartService.getProductsInCart() );
        mav.addObject( "totalPrice", cartService.getTotal() );
        System.out.println(cartService.getProductsInCart()+" total: "+ cartService.getTotal());
        return mav;
    }

    @GetMapping({"/checkout"})
    public ModelAndView checkout()
    {
        cartService.checkout();
        return viewBasket();
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
