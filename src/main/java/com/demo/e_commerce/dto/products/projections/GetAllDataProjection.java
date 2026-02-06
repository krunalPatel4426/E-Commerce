package com.demo.e_commerce.dto.products.projections;

import java.math.BigDecimal;

public interface GetAllDataProjection {
    Long getId();
    String getName();
    BigDecimal getPrice();
    Integer getQuantity();
    String getCategoryName();
}
