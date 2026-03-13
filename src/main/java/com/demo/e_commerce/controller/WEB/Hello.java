package com.demo.e_commerce.controller.WEB;

import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Hello {

    @GetMapping("/design")
    public ModelAndView index() {
        System.out.println("Working.");
        ModelAndView mv = new ModelAndView("design/design");
        return mv;
    }

}
