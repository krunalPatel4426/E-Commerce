package com.demo.e_commerce.repository.categoryrepo;

import com.demo.e_commerce.dto.categories.projections.GetAllCategoriesProjection;
import com.demo.e_commerce.model.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    Optional<CategoryEntity> findByNameAndIsDeleted(String name, int  isDeleted);

    Optional<CategoryEntity> findByIdAndIsDeleted(Long id, int  isDeleted);

    @Query(nativeQuery = true, value = """
    SELECT 
        c.id as id,
        c.name as name
    FROM categories c WHERE c.is_deleted = 0;
""")
    List<GetAllCategoriesProjection> getAllCategories();
}