package com.demo.e_commerce.repository.productrepo;

import com.demo.e_commerce.dto.products.projections.GetAllDataProjection;
import com.demo.e_commerce.dto.products.projections.GetProductDataProjection;
import com.demo.e_commerce.model.ProductEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByIdAndIsDeleted(Long productId, int isDeleted);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = """
    UPDATE products set is_deleted = :isDeleted where id = :productId;
""")
    int deleteProduct(Long productId, int isDeleted);

    @Query(nativeQuery = true, value = """
    SELECT 
        p.id as id,
        p.name as name,
        p.price as price,
        p.quantity as quantity,
        c.name as categoryName
    FROM products p 
    LEFT JOIN categories c on c.id = p.category_id
    WHERE p.is_deleted = 0;
""")
    List<GetAllDataProjection> getAllProducts();

    @Query(nativeQuery = true, value = """
    SELECT 
        p.id as id,
        p.name as name,
        p.price as price,
        p.quantity as quantity,
        p.description as description,
        p.created_at as createdAt,
        p.updated_at as updatedAt,
        c.name as categoryName
    FROM products p 
    LEFT JOIN categories c on c.id = p.category_id
    WHERE p.is_deleted = 0 AND p.id = :id;
""")
    Optional<GetProductDataProjection> getProduct(Long id);
}
