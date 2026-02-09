package com.demo.e_commerce.controller.WEB.productView;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/product")
public class ProductViewController {

    @GetMapping("/{id}")
    public ModelAndView productView(@PathVariable String id) {
        return new ModelAndView("Product/product-details");
    }
}
