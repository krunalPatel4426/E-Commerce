package com.demo.e_commerce.dto.cartDto;

import lombok.Data;

@Data
public class UpdateCartItemRequest {
    private Long cartItemId;
    private Integer newQuantity;
}
