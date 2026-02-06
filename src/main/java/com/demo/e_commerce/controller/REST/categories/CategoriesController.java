package com.demo.e_commerce.controller.REST.categories;

import com.demo.e_commerce.service.CategoriesService.interfaces.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
public class CategoriesController {

    @Autowired
    private CategoriesService categoriesService;

    @GetMapping("")
    public ResponseEntity<?> getCategories() {
        return categoriesService.getAllCategories();
    }
}
