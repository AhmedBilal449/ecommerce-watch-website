package com.cstp.shop.service;

import com.cstp.shop.model.Product;

import java.math.BigDecimal;
import java.util.Map;

public interface CartService
{
    void addProduct(Product product);

    void removeProduct(Product product);

    Map<Product, Integer> getProductsInCart();

    float getTotal();

    void checkout();

}
