package com.demo.e_commerce.controller.REST.product;

import com.demo.e_commerce.service.productService.interfaces.ProductService;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService  productService;

    @GetMapping("")
    public ResponseEntity<?> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProducts(@PathVariable Long productId) {
        return productService.getProduct(productId);
    }

}
