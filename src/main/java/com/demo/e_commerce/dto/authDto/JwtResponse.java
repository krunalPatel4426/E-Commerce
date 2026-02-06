package com.demo.e_commerce.dto.authDto;

import lombok.Data;

import java.util.List;

@Data
public class JwtResponse {
    private String token;
    private String type =  "Bearer";
    private Long id;
    private String username;
    private String email;
    private List<String> roles;
}
