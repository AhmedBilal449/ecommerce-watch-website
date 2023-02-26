package com.cstp.shop.controller;

import com.cstp.shop.service.CartService;
import com.cstp.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    private final ProductService productService;

    @Autowired
    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @GetMapping("/")
    public String shoppingCart() {
        System.out.println(cartService.getProductsInCart()+" total: "+ cartService.getTotal());
        return "shoppingCart()";
    }

    @GetMapping("/add/{productId}")
    public String addProductToCart(@PathVariable("productId") Long productId) {
        productService.findById(productId).ifPresent(cartService::addProduct);
        System.out.println(cartService.getProductsInCart()+" total: "+ cartService.getTotal());
        return "addProductToCart()";
    }

    @GetMapping("/remove/{productId}")
    public String removeProductFromCart(@PathVariable("productId") Long productId) {
        productService.findById(productId).ifPresent(cartService::removeProduct);
        return "removeProductFromCart()";
    }

    @GetMapping("/checkout")
    public String checkout() {
            cartService.checkout();
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