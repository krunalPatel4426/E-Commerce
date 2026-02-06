package com.demo.e_commerce.dto.products;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GetAllProductResponseDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private String categoryName;
    private boolean isAvailable;
}
