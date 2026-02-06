package com.demo.e_commerce.service.productService.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {

    ResponseEntity<?> getAllProducts();

    ResponseEntity<?> getProduct(Long productId);
}
