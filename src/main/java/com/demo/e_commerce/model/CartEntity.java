package com.demo.e_commerce.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
@Data
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserEntity user;

    @Column(name = "totalAmount")
    private BigDecimal totalAmount =  BigDecimal.ZERO;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItemEntity> cartItems = new ArrayList<>();
}
