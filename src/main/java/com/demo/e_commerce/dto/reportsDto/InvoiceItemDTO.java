package com.demo.e_commerce.dto.reportsDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceItemDTO {
    private String productName;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal lineTotal;
}