package com.demo.e_commerce.controller.REST.product;

import com.demo.e_commerce.service.productService.interfaces.ProductService;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

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

    @GetMapping("/filter")
    public ResponseEntity<?> getFilteredProducts(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return productService.getFilteredProduct(search,categoryId,minPrice,maxPrice,sortBy,sortDir,page,size);
    }

}
