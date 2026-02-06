package com.demo.e_commerce.service.cartService.interfaces;

import com.demo.e_commerce.dto.cartDto.AddProductCartRequest;
import com.demo.e_commerce.dto.cartDto.UpdateCartItemRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface CartService {

    ResponseEntity<?> addProductIntoCart(AddProductCartRequest request);

    ResponseEntity<?> updateCartItems(UpdateCartItemRequest request);

    ResponseEntity<?> removeCartItem(Long cartItemId);

    ResponseEntity<?> getCart(Long userId);
}
