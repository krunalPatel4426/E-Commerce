package com.demo.e_commerce.controller.WEB.orderView;

import com.demo.e_commerce.controller.WEB.helper.Helper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/orders")
public class OrderViewController {

    @Autowired
    private Helper helper;

    @GetMapping("")
    public ModelAndView orderHistoryPage(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        if(helper.isLoggedIn(request)) {
            mv.setViewName("Order/order-history");
        }else {
            mv.setViewName("redirect:/auth/login");
        }
        return mv;
    }

    @GetMapping("/{id}")
    public ModelAndView orderDetailPage(HttpServletRequest request, @PathVariable String id) {
        ModelAndView mv = new ModelAndView();
        if(helper.isLoggedIn(request)) {
            mv.setViewName("Order/order-detail");
        }else  {
            mv.setViewName("redirect:/auth/login");
        }
        return mv;
    }
}
