package com.demo.e_commerce.dto.commonDto;

import com.demo.e_commerce.dto.orderDto.response.OrderResponseDto;
import lombok.Data;

import java.util.List;

@Data
public class GetAllOrderDto {
    private String message;
    private boolean success;
    private List<OrderResponseDto> data;
}
