package com.demo.e_commerce.dto.products;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class GetProductResponseDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private Integer quantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
