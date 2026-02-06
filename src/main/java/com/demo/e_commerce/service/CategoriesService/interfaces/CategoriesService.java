package com.demo.e_commerce.service.CategoriesService.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface CategoriesService {

    ResponseEntity<?> getAllCategories();
}
