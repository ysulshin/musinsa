package com.display.product.item.repository;

import com.display.product.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {
    boolean existsByBrandIdAndCategoryIdAndIdNot(Long brandId, Long categoryId, Long itemId);

    List<Item> findByBrandId(Long brandId);
}
