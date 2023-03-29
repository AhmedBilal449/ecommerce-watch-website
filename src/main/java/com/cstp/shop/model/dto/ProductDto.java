package com.cstp.shop.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ProductDto {
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

    private int rating;
}
