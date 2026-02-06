package com.demo.e_commerce.dto.cartDto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemsInCartDto {
    private Long cartItemId;
    private BigDecimal totalPrice;
    private Integer quantity;
    private ProductInCartDto product;
}
