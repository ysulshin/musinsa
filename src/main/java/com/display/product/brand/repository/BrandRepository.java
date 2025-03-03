package com.display.product.brand.repository;

import com.display.product.brand.dto.CategoryPriceDTO;
import com.display.product.brand.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long>, BrandRepositoryCustom {
    @Query(value = """
    SELECT b.*
    FROM brand b
    WHERE b.id = (
        SELECT brand_id
        FROM (
            SELECT category_id, brand_id, MIN(price) AS min_price
            FROM item
            GROUP BY category_id, brand_id
        ) AS category_min_prices
        GROUP BY brand_id
        ORDER BY SUM(min_price) ASC
        LIMIT 1
    )
    """, nativeQuery = true)
    Optional<Brand> findBrandWithLowestCategoryMinPrice();

    boolean existsByName(String name);

     Optional<Brand> findByName(String name);

}
