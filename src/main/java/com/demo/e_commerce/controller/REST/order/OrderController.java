package com.demo.e_commerce.controller.REST.order;

import com.demo.e_commerce.dto.orderDto.MakeOrderRequestDto;
import com.demo.e_commerce.service.orderService.interfaces.OrderService;
import com.demo.e_commerce.service.reportsService.interfaces.JasperReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private JasperReportService jasperReportService;

    @PostMapping("")
    public ResponseEntity<?> createOrder(@RequestBody MakeOrderRequestDto request){
        return orderService.createOrder(request);
    }

    @GetMapping("/{id}/bill")
    public ResponseEntity<byte[]> downloadBill(@PathVariable Long id) {
        return orderService.downloadBill(id);
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
