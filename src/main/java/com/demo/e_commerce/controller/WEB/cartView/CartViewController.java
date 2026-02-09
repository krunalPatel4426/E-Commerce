package com.demo.e_commerce.controller.WEB.cartView;

import com.demo.e_commerce.controller.WEB.helper.Helper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/cart")
public class CartViewController {

    @Autowired
    private Helper helper;

    @GetMapping("")
    public ModelAndView getCart(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        if(helper.isLoggedIn(request)) {
            mv.setViewName("Cart/cart");
        }else {
            mv.setViewName("redirect:/auth/login");
        }
        return mv;
    }
}
