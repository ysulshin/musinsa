package com.display.product.category.repository;

import com.display.product.brand.dto.CategoryPriceDTO;
import com.display.product.category.dto.BrandPriceDTO;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepositoryCustom {
    BrandPriceDTO findPriceByCategoryName(@Param("brandName") String brandName, boolean isMin);

}
