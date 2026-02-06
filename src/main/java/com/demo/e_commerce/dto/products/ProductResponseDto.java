package com.demo.e_commerce.dto.products;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProductResponseDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private int deleted;
    private Integer quantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
