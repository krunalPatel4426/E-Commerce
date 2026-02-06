package com.demo.e_commerce.dto.orderDto.response;

import com.demo.e_commerce.enums.OrderStatus;
import com.demo.e_commerce.model.OrderItemEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDto {
    private Long orderId;
    private BigDecimal totalOrderPrice;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private List<OrderItemInOrder> orderItem;
}
