package com.demo.e_commerce.service.productService.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface ProductService {

    ResponseEntity<?> getAllProducts();

    ResponseEntity<?> getProduct(Long productId);

    ResponseEntity<?> getFilteredProduct(String search, Long categoryId, BigDecimal minPrice, BigDecimal maxPrice, String sortBy, String sortDir, int page, int size);
}
