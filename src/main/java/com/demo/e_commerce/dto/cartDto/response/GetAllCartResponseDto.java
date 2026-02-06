package com.demo.e_commerce.dto.cartDto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class GetAllCartResponseDto {
    private Long CartId;
    private BigDecimal total;
    private List<CartItemsInCartDto> cartItem;
}
