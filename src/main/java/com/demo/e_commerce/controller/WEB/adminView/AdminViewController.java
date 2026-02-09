package com.demo.e_commerce.controller.WEB.adminView;

import com.demo.e_commerce.controller.WEB.helper.Helper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminViewController {

    @Autowired
    private Helper helper;

    @GetMapping("/categories")
    public ModelAndView categoryPage(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        if(helper.isAuthenticated(request)) {
        modelAndView.setViewName("Admin/Category/category");
        }else{
            modelAndView.setViewName("redirect:/");
        }
        return modelAndView;
    }

    @GetMapping("/products")
    public ModelAndView productPage(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        if (helper.isAuthenticated(request)) {
            modelAndView.setViewName("Admin/Product/product");
        } else {
            modelAndView.setViewName("redirect:/");
        }

        return modelAndView;
    }

}
