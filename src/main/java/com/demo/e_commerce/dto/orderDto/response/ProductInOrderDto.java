package com.demo.e_commerce.dto.orderDto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductInOrderDto {
    private Long productId;
    private String name;
    private BigDecimal price;
    private String category;
}
