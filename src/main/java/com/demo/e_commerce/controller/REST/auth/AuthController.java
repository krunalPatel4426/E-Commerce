package com.demo.e_commerce.controller.REST.auth;

import com.demo.e_commerce.dto.authDto.LoginRequest;
import com.demo.e_commerce.dto.authDto.SignUpRequest;
import com.demo.e_commerce.service.authService.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Validated @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Validated @RequestBody SignUpRequest signUpRequest) {
        return authService.registerUser(signUpRequest);
    }
}