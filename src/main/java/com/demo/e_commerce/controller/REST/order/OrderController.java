package com.demo.e_commerce.controller.REST.order;

import com.demo.e_commerce.dto.orderDto.MakeOrderRequestDto;
import com.demo.e_commerce.service.orderService.interfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("")
    public ResponseEntity<?> createOrder(@RequestBody MakeOrderRequestDto request){
        return orderService.createOrder(request);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable("orderId") Long orderId){
        return orderService.getOrder(orderId);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getOrders(@PathVariable Long  userId){
        return orderService.getAllOrderForUser(userId);
    }

}
