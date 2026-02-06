package com.demo.e_commerce.service.cartService.helpers;


import com.demo.e_commerce.model.CartEntity;
import com.demo.e_commerce.model.CartItemEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class HelperMethodCartService {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public BigDecimal calculateTotalPrice(CartEntity cartEntity) {
        BigDecimal totalPrice = cartEntity
                .getCartItems().stream()
                .map(CartItemEntity::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalPrice;
    }
}
