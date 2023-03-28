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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


@Controller
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

    @GetMapping({"/", "/home"})
    public ModelAndView viewHome()
    {
        ModelAndView mav = new ModelAndView("home");
        return mav;
    }

    @GetMapping("/login")
    public ModelAndView viewLogin()
    {
        ModelAndView mav = new ModelAndView("login");
        mav.addObject("signupDto", new SignupDto());
        mav.addObject("loginDto", new LoginDto());
        return mav;
    }

    @GetMapping("/products")
    @ResponseBody
    public ModelAndView viewProducts(@RequestParam(defaultValue = "all") String category)
    {
        ModelAndView mav = new ModelAndView("product");
        if (category.equals("all"))
            mav.addObject( "products", productRepository.findAll() );
        else
            mav.addObject( "products", productRepository.findAllByCategory(category) );


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
    public ModelAndView checkout(HttpServletRequest request)
    {
        cartService.checkout(request);
        return viewBasket();
    }

    @GetMapping("/about")
    public ModelAndView viewAbout()
    {
        ModelAndView mav = new ModelAndView("about");
        return mav;
    }

    @GetMapping("/contact")
    public ModelAndView viewContact()
    {
        ModelAndView mav = new ModelAndView("contact");
        return mav;
    }

}
