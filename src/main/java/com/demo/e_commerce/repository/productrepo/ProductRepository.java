package com.demo.e_commerce.repository.productrepo;

import com.demo.e_commerce.dto.products.projections.GetAllDataProjection;
import com.demo.e_commerce.dto.products.projections.GetProductDataProjection;
import com.demo.e_commerce.model.ProductEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
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
            c.name as categoryName
        FROM products p 
        LEFT JOIN categories c on c.id = p.category_id
        WHERE p.is_deleted
         = 0
        AND (:search IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')))
        AND (:categoryId IS NULL OR p.category_id = :categoryId)
        AND (:minPrice IS NULL OR p.price >= :minPrice)
        AND (:maxPrice IS NULL OR p.price <= :maxPrice)
        
        ORDER BY 
            CASE WHEN :sortBy = 'price' AND :sortDir = 'asc' THEN p.price END ASC,
            CASE WHEN :sortBy = 'price' AND :sortDir = 'desc' THEN p.price END DESC,
            
            CASE WHEN :sortBy = 'name' AND :sortDir = 'asc' THEN p.name END ASC,
            CASE WHEN :sortBy = 'name' AND :sortDir = 'desc' THEN p.name END DESC,
            
            CASE WHEN :sortBy = 'id' AND :sortDir = 'asc' THEN p.id END ASC,
            CASE WHEN :sortBy = 'id' AND :sortDir = 'desc' THEN p.id END DESC
            
        LIMIT :limit OFFSET :offset
    """)
    List<GetAllDataProjection> getFilteredProductsManual(
            @Param("search") String search,
            @Param("categoryId") Long categoryId,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("sortBy") String sortBy,
            @Param("sortDir") String sortDir,
            @Param("limit") int limit,
            @Param("offset") int offset
    );

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
