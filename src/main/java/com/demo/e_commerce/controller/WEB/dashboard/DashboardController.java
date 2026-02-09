package com.demo.e_commerce.controller.WEB.dashboard;

import com.demo.e_commerce.model.AdminNavLink;
import com.demo.e_commerce.repository.adminnavlinkrepo.AdminNavLinkRepository;
import com.demo.e_commerce.security.jwt.JwtUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    private JwtUtils  jwtUtils;

    @Autowired
    private AdminNavLinkRepository adminNavLinkRepository;

    @GetMapping
    public ModelAndView dashboard(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("Dashboard/dashboard");

        List<AdminNavLink> adminLinks = new ArrayList<>();
        boolean isLoggedIn = false;
        List<String> roles = new ArrayList<>();
        String username = "GUEST";
        String token = getJwtFromCookies(request);

        if(token != null && jwtUtils.validateJwtToken(token)) {
            isLoggedIn = true;
            roles = jwtUtils.getRolesFromJwtToken(token);
            username = jwtUtils.getUserNameFromJwtToken(token);
            if(roles != null && roles.contains("ROLE_ADMIN")){
                adminLinks = adminNavLinkRepository.findByIsActiveTrue();
            }
            mv.addObject("adminLinks", adminLinks);
            mv.addObject("isLoggedIn", isLoggedIn);
            mv.addObject("username", username);
        }

        return mv;
    }

    private String getJwtFromCookies(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("auth_token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
