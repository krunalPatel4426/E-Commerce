package com.demo.e_commerce.dto.cartDto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DeleteCartItemDto {
    private Long cartId;
    private BigDecimal newTotalAmount;
}
