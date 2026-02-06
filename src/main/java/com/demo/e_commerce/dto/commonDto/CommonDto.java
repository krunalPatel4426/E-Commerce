package com.demo.e_commerce.dto.commonDto;

import lombok.Data;

@Data
public class CommonDto {
    private String message;
    private boolean success;
    private Object data;
}
