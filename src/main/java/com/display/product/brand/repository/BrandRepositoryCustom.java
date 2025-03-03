package com.display.product.brand.repository;

import com.display.product.brand.dto.CategoryPriceDTO;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BrandRepositoryCustom {
    List<CategoryPriceDTO> findMinPricePerCategoryByBrandId(@Param("brandId") Long brandId);

}
