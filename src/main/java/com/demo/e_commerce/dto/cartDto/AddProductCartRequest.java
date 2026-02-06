package com.demo.e_commerce.dto.cartDto;

import lombok.Data;

@Data
public class AddProductCartRequest {
    private Long userId;
    private Long productId;
    private Integer quantity;
}
