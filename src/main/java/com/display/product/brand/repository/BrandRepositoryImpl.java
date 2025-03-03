package com.display.product.brand.repository;

import com.display.product.brand.dto.CategoryPriceDTO;
import com.display.product.category.entity.QCategory;
import com.display.product.item.entity.QItem;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BrandRepositoryImpl implements BrandRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<CategoryPriceDTO> findMinPricePerCategoryByBrandId(Long brandId) {
        QItem item = QItem.item;
        QCategory category = QCategory.category;

        List<CategoryPriceDTO> results = queryFactory
                .select(Projections.constructor(CategoryPriceDTO.class,
                        category.name,
                        item.price.min()))
                .from(item)
                .join(item.category, category)
                .where(item.brand.id.eq(brandId))
                .groupBy(category.name)
                .orderBy(category.id.asc())
                .fetch();

        return results;
    }
}
