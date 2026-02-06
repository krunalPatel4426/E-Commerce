package com.demo.e_commerce.service.orderService.interfaces;

import com.demo.e_commerce.dto.orderDto.MakeOrderRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    ResponseEntity<?> createOrder(MakeOrderRequestDto request);

    ResponseEntity<?> getOrder(Long orderId);

    ResponseEntity<?> getAllOrderForUser(Long userId);
}
