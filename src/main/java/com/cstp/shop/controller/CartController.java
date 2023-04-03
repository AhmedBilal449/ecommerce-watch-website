package com.cstp.shop.controller;

import com.cstp.shop.model.dto.CartDto;
import com.cstp.shop.service.CartService;
import com.cstp.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    private final ProductService productService;

    @Autowired
    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @GetMapping("/add/{productId}")
    public String addProductToCart(@PathVariable("productId") Long productId) {
        productService.findById(productId).ifPresent(cartService::addProduct);
        return "addProductToCart()";
    }

    @PostMapping("/add")
    public String formAddProductToCart(@Valid @ModelAttribute CartDto cartDto, BindingResult result, Model model) {
        productService.findById(cartDto.getId()).ifPresent(cartService::addProduct);
        return "redirect:/products";
    }

    @GetMapping("/remove/{productId}")
    public String removeProductFromCart(@PathVariable("productId") Long productId) {
        productService.findById(productId).ifPresent(cartService::removeProduct);
        return "removeProductFromCart()";
    }

    @GetMapping("/checkout")
    public String checkout(HttpServletRequest request) {
            cartService.checkout(request);
        return "checkout()";
    }

//    @GetMapping("/")
//    public ModelAndView shoppingCart() {
//        ModelAndView modelAndView = new ModelAndView("/shoppingCart");
//        modelAndView.addObject("products", shoppingCartService.getProductsInCart());
//        modelAndView.addObject("total", shoppingCartService.getTotal());
//        return modelAndView;
//    }
//
//    @GetMapping("/addProduct/{productId}")
//    public ModelAndView addProductToCart(@PathVariable("productId") Long productId) {
//        productService.findById(productId).ifPresent(shoppingCartService::addProduct);
//        return shoppingCart();
//    }
//
//    @GetMapping("/removeProduct/{productId}")
//    public ModelAndView removeProductFromCart(@PathVariable("productId") Long productId) {
//        productService.findById(productId).ifPresent(shoppingCartService::removeProduct);
//        return shoppingCart();
//    }
//
//    @GetMapping("/checkout")
//    public ModelAndView checkout() {
//            shoppingCartService.checkout();
//        return shoppingCart();
//    }
}