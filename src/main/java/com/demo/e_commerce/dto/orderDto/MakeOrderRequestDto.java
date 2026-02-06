package com.demo.e_commerce.dto.orderDto;

import lombok.Data;

@Data
public class MakeOrderRequestDto {
    private Long userId;
    private Long cartId;
}
