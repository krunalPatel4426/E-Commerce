package com.demo.e_commerce.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "cart_items")
@Data
public class CartItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private CartEntity cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private BigDecimal price;
}

