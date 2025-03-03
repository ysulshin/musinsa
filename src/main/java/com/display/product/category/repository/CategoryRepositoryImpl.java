package com.display.product.category.repository;

import com.display.product.brand.entity.QBrand;
import com.display.product.category.dto.BrandPriceDTO;
import com.display.product.category.entity.QCategory;
import com.display.product.item.entity.QItem;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepositoryCustom{
    private final JPAQueryFactory queryFactory;


    public BrandPriceDTO findPriceByCategoryName(String categoryName, boolean isMin) {
        QItem item = QItem.item;
        QCategory category = QCategory.category;
        QBrand brand = QBrand.brand;

        OrderSpecifier<Integer> orderByPrice = isMin ? item.price.asc() : item.price.desc();

        return queryFactory
                .select(Projections.constructor(BrandPriceDTO.class,
                        brand.name,
                        item.price))
                .from(item)
                .join(item.category, category)
                .join(item.brand, brand)
                .where(category.name.eq(categoryName))
                .groupBy(brand.name, item.price)
                .orderBy(orderByPrice)
                .limit(1)
                .fetchOne();
    }


}
