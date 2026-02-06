package com.demo.e_commerce.dto.orderDto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemInOrder {
    private Long orderItemId;
    private Integer quantity;
    private BigDecimal orderItemPrice;
    private ProductInOrderDto product;
}
