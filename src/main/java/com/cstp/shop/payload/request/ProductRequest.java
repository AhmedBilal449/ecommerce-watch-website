package com.cstp.shop.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
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
}
