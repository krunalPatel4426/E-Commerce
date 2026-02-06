package com.demo.e_commerce.dto.products.projections;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface GetProductDataProjection {
    Long getId();
    String getName();
    BigDecimal getPrice();
    Integer getQuantity();
    String getCategoryName();
    String getDescription();
    LocalDateTime getCreatedAt();
    LocalDateTime getUpdatedAt();
}
