package com.cstp.shop.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProductRequest
{
    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String category;

    @NotBlank
    private String imgpath;

    @NotNull
    private float price;

    @NotNull
    private int stock;

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public String getCategory(){
        return category;
    }

    public String getImgpath(){
        return imgpath;
    }

    public float getPrice(){
        return price;
    }

    public int getStock(){
        return stock;
    }
}
