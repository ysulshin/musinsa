package com.display.product.category.repository;

import com.display.product.category.dto.MinPriceItemDTO;
import com.display.product.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryCustom {
    @Query(value = """
        WITH RankedItems AS (
            SELECT 
                c.id AS category_id,
                c.name AS category_name,
                b.id AS brand_id,
                b.name AS brand_name,
                i.price,
                ROW_NUMBER() OVER (
                    PARTITION BY c.id 
                    ORDER BY i.price ASC, b.id DESC
                ) AS rn
            FROM item i
            JOIN category c ON c.id = i.category_id
            JOIN brand b ON b.id = i.brand_id
        )
        SELECT category_id, category_name, brand_id, brand_name, price
        FROM RankedItems
        WHERE rn = 1
        ORDER BY category_id
    """, nativeQuery = true)
    List<MinPriceItemDTO> findCategoryMinPriceBrandInfo();

    Optional<Category> findByName(String name);
}
