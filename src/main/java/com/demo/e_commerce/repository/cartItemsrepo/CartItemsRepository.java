package com.demo.e_commerce.repository.cartItemsrepo;

import com.demo.e_commerce.model.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItemEntity, Long> {

    Optional<CartItemEntity> findById(Long cartItemId);

    int deleteAllByCartId(Long cartId);

    CartItemEntity findByCartIdAndProductId(Long cartId, Long productId);
}
