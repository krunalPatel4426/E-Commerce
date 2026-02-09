package com.demo.e_commerce.service.adminService.interfaces;

import com.demo.e_commerce.dto.categories.AddCategoryDto;
import com.demo.e_commerce.dto.products.AddProductDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public interface AdminService {
    public ResponseEntity<?> createCategory(@RequestBody AddCategoryDto addCategoryDto);

    ResponseEntity<?> updateCategory(Long id, AddCategoryDto addCategoryDto);

    ResponseEntity<?> createProduct(AddProductDto addProductDto);

    ResponseEntity<?> updateProduct(Long productId, AddProductDto addProductDto);

    ResponseEntity<?> deleteProduct(Long productId);

    ResponseEntity<?> deleteCategory(Long id);
}
