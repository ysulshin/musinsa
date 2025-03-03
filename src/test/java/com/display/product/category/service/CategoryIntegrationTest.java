package com.display.product.category.service;

import com.display.product.brand.repository.BrandRepository;
import com.display.product.category.dto.AllCategoryMinPriceResponse;
import com.display.product.category.dto.MinMaxPriceResponse;
import com.display.product.category.repository.CategoryRepository;
import com.display.product.item.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class CategoryIntegrationTest {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void getMINPriceByCategory_Success() {
        AllCategoryMinPriceResponse response = categoryService.getMINPriceByCategory();

        assertEquals(response.getTotalCount(), 34100);
        assertEquals(response.getMinPriceItemDTOList().get(0).getCategoryName(), "상의");
        assertEquals(response.getMinPriceItemDTOList().get(0).getBrandName(), "C");
        assertEquals(response.getMinPriceItemDTOList().get(0).getPrice(), 10000);
        assertEquals(response.getMinPriceItemDTOList().size(), 8);
    }

    @Test
    void getMinMaxPriceBrandByCategory_Success() {
        String categoryName = "상의";
        MinMaxPriceResponse response = categoryService.getMinMaxPriceBrandByCategory(categoryName);

        assertEquals(response.getCategoryName(), categoryName);
        assertEquals(response.getMinBrandPrice().getBrandName(), "C");
        assertEquals(response.getMinBrandPrice().getPrice(), 10000);
        assertEquals(response.getMaxBrandPrice().getBrandName(), "I");
        assertEquals(response.getMaxBrandPrice().getPrice(), 11400);
    }
}
