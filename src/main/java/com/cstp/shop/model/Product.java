package com.cstp.shop.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 64)
    private String name;

    @NotBlank
    @Size(max = 255)
    private String description;

    @NotBlank
    @Size(max = 120)
    private String category;

    @NotBlank
    @Size(max = 255)
    private String imgpath;

    @NotNull
    private float price;

    @NotNull
    private int stock;

    public Product() {
    }

    public Product(String name, String description, String category, String imgpath, float price, int stock) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.imgpath = imgpath;
        this.price = price;
        this.stock = stock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public float getTotal(int quantity)
    {
        return price * quantity;
    }

}
