package com.demo.e_commerce.controller.REST.cart;

import com.demo.e_commerce.dto.cartDto.AddProductCartRequest;
import com.demo.e_commerce.dto.cartDto.UpdateCartItemRequest;
import com.demo.e_commerce.service.cartService.interfaces.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService  cartService;

    @PostMapping("/add")
    public ResponseEntity<?> addProductIntoCart(@RequestBody AddProductCartRequest request){
        return cartService.addProductIntoCart(request);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProductInCart(@RequestBody UpdateCartItemRequest request){
        return cartService.updateCartItems(request);
    }

    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<?> removeProductFromCart(@PathVariable("cartItemId") Long cartItemId){
        return cartService.removeCartItem(cartItemId);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getCart(@PathVariable Long userId){
        return cartService.getCart(userId);
    }
}
