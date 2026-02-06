package com.demo.e_commerce.controller.WEB;

import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Hello {

    @GetMapping("/hello")
    public ModelAndView index() {
        System.out.println("Hello World");
        ModelAndView mv = new ModelAndView("Hello");
        return mv;
    }

}
