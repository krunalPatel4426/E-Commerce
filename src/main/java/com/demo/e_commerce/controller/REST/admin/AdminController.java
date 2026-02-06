package com.demo.e_commerce.controller.REST.admin;

import com.demo.e_commerce.dto.categories.AddCategoryDto;
import com.demo.e_commerce.dto.products.AddProductDto;
import com.demo.e_commerce.service.adminService.interfaces.AdminService;
import jdk.jfr.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/categories")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> createCategory(@RequestBody AddCategoryDto addCategoryDto) {
        return adminService.createCategory(addCategoryDto);
    }


    @PutMapping("/categories/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody AddCategoryDto addCategoryDto) {
        return adminService.updateCategory(id, addCategoryDto);
    }

    @PostMapping("/products")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> createProduct(@RequestBody AddProductDto addProductDto){
        return adminService.createProduct(addProductDto);
    }

    @PutMapping("/products/{productId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> updateProduct(@PathVariable Long productId, @RequestBody AddProductDto addProductDto){
        return adminService.updateProduct(productId, addProductDto);
    }

    @DeleteMapping("/products/{productId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId){
        return adminService.deleteProduct(productId);
    }
}
