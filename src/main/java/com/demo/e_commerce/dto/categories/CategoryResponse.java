package com.demo.e_commerce.dto.categories;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoryResponse {
    private Long id;
    private String name;
    private boolean isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
