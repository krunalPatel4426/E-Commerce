package com.demo.e_commerce.dto.products;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddProductDto {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private Long categoryId;
}
