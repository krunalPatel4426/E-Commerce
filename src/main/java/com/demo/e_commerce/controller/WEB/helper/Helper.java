package com.demo.e_commerce.controller.WEB.helper;

import com.demo.e_commerce.security.jwt.JwtUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Helper {

    @Autowired
    private JwtUtils jwtUtils;

    public boolean isAuthenticated(HttpServletRequest request) {
        String token = null;
        if(request.getCookies() !=  null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("auth_token")) {
                    token = cookie.getValue();
                }
            }
        }

        if(jwtUtils.validateJwtToken(token)) {
            List<String> roles = jwtUtils.getRolesFromJwtToken(token);
            if(roles != null && roles.size() > 0) {
                if(roles.contains("ROLE_ADMIN")) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isLoggedIn(HttpServletRequest request) {
        if(request.getCookies() !=  null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("auth_token")) {
                    return true;
                }
            }
        }
        return false;
    }
}
