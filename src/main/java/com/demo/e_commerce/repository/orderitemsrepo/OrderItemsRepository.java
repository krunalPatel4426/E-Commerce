package com.demo.e_commerce.repository.orderitemsrepo;

import com.demo.e_commerce.model.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItemEntity,Long> {
}
