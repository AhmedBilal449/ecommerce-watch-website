package com.cstp.shop.service.impl;


import com.cstp.shop.model.Product;
import com.cstp.shop.repository.ProductRepository;
import com.cstp.shop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class CartServiceImpl implements CartService {

    private final ProductRepository productRepository;

    private Map<Product, Integer> products = new HashMap<>();

    @Autowired
    public CartServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void addProduct(Product product) {
        if (products.containsKey(product)) {
            products.replace(product, products.get(product) + 1);
        } else {
            products.put(product, 1);
        }
    }

    @Override
    public void removeProduct(Product product) {
        if (products.containsKey(product)) {
            if (products.get(product) > 1)
                products.replace(product, products.get(product) - 1);
            else if (products.get(product) == 1) {
                products.remove(product);
            }
        }
    }

    @Override
    public Map<Product, Integer> getProductsInCart() {
        return Collections.unmodifiableMap(products);
    }

    @Override
    public void checkout() {
        Product product;
        for (Map.Entry<Product, Integer> entry : products.entrySet())
        {
            product = productRepository.findById(entry.getKey().getId())
                    .orElse(null);

            System.out.println(product);

            if (product == null) continue;
            if (entry.getValue() < product.getStock()) {
                entry.getKey().setStock(product.getStock() - entry.getValue());
            }
        }
        productRepository.saveAllAndFlush(products.keySet());
        products.clear();
    }

    @Override
    public float getTotal() {

        return products.entrySet().stream()
                .map(entry -> entry.getKey().getPrice()*entry.getValue())
                .reduce(Float::sum)
                .orElse(0.0F);
    }
}