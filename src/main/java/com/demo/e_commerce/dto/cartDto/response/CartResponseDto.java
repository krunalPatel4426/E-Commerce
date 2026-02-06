package com.demo.e_commerce.dto.cartDto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartResponseDto {
    private Long CartId;
    private BigDecimal total;
    private CartItemsInCartDto cartItem;
}
