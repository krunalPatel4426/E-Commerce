package com.demo.e_commerce.repository.cartrepo;

import com.demo.e_commerce.model.CartEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CartRepository extends JpaRepository<CartEntity, Long> {

    Optional<CartEntity> findByUserId(Long user_id);

//    @Modifying
//    @Transactional
//    @Query(nativeQuery = true, value = """
//    UPDATE carts c
//    SET total_amount = c.total_amount + ci.price
//    FROM cart_items ci WHERE ci.cart_id = c.id AND c.id = :cartId
//""")
//    int updateTotalAmount(Long cartId);
}
