package com.demo.e_commerce.repository.orderrepo;

import com.demo.e_commerce.model.OrderEntity;
import com.demo.e_commerce.model.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    Optional<OrderEntity> findById(Long orderId, int i);
}
