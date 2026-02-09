package com.demo.e_commerce.controller.WEB.authView;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/auth")
public class AuthViewController {
    @GetMapping("/login")
    public ModelAndView loginPage() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("Auth/login");
        return mv;
    }

    @GetMapping("/register")
    public ModelAndView registerPage() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("Auth/register");
        return mv;
    }
}
