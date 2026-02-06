package com.demo.e_commerce.dto.cartDto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductInCartDto {
    private Long productId;
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private String categoryName;
}
