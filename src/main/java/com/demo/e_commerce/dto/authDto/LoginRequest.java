package com.demo.e_commerce.dto.authDto;

import lombok.Data;

@Data
public class LoginRequest {
    private String usernameOrEmail;
    private String password;
}
