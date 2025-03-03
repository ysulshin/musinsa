package com.display.product.brand.service;

import com.display.product.brand.dto.BrandCreateItem;
import com.display.product.brand.dto.BrandCreateRequest;
import com.display.product.brand.dto.TotalMinPriceBrandResponse;
import com.display.product.brand.entity.Brand;
import com.display.product.brand.repository.BrandRepository;
import com.display.product.category.entity.Category;
import com.display.product.category.repository.CategoryRepository;
import com.display.product.exception.ApiException;
import com.display.product.item.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class BrandIntegrationTest {
    @Autowired
    private BrandService brandService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void getMINPriceByCategory_Success() {
        TotalMinPriceBrandResponse response = brandService.getBrandWithMinPrice();

        assertEquals(response.getBrandName(), "D");
        assertEquals(response.getCategoryPriceList().size(), 8);
        assertEquals(response.getCategoryPriceList().get(0).getCategoryName(), "상의");
        assertEquals(response.getCategoryPriceList().get(0).getPrice(), 10100);
        assertEquals(response.getCategoryPriceList().get(7).getCategoryName(), "액세서리");
        assertEquals(response.getCategoryPriceList().get(7).getPrice(), 2000);
        assertEquals(response.getTotalPrice(), 36100);

    }


    @Test
    void createBrand_Success() {
        BrandCreateRequest request = new BrandCreateRequest("NewBrand"
                , List.of(new BrandCreateItem(1L, 2000)
                       , new BrandCreateItem(2L, 3000)
                       , new BrandCreateItem(3L, 4000)
                       , new BrandCreateItem(4L, 5000)
                       , new BrandCreateItem(5L, 6000)
                       , new BrandCreateItem(6L, 7000)
                       , new BrandCreateItem(7L, 8000)
                       , new BrandCreateItem(8L, 9000)    ));
        brandService.createBrand(request);
        assertThat(brandRepository.findAll()).hasSize(10);
    }

    @Test
    void createBrand_ThrowException_AlreadyExit() {
        BrandCreateRequest request = new BrandCreateRequest("A", List.of(new BrandCreateItem(1L, 2000)));
        assertThrows(ApiException.class, () -> brandService.createBrand(request));
    }

    @Test
    void createBrand_ThrowException_InvalidCategory_CategoryNotFound() {
        BrandCreateRequest request = new BrandCreateRequest("NewBrand"
                , List.of(new BrandCreateItem(1L, 2000)
                , new BrandCreateItem(2L, 3000)
                , new BrandCreateItem(3L, 4000)
                , new BrandCreateItem(4L, 5000)
                , new BrandCreateItem(5L, 6000)
                , new BrandCreateItem(6L, 7000)
                , new BrandCreateItem(7L, 8000)
                , new BrandCreateItem(111L, 9000)    ));
        assertThrows(ApiException.class, () -> brandService.createBrand(request));
    }

    @Test
    void createBrand_ThrowException_InvalidCategory_NotAllCategory() {
        BrandCreateRequest request = new BrandCreateRequest("NewBrand"
                , List.of(new BrandCreateItem(1L, 2000)
                , new BrandCreateItem(4L, 5000)
                , new BrandCreateItem(5L, 6000)
                , new BrandCreateItem(6L, 7000)
                , new BrandCreateItem(7L, 8000)
                , new BrandCreateItem(8L, 9000)    ));
        assertThrows(ApiException.class, () -> brandService.createBrand(request));
    }

    @Test
    void updateBrand_Success() {
        Long targetBrandId = 1L;
        brandService.updateBrand(targetBrandId, "UPDATED BRAND");

        Brand updatedBrand = brandRepository.findById(targetBrandId).orElseThrow();
        assertThat(updatedBrand.getName()).isEqualTo("UPDATED BRAND");
    }

    @Test
    void updateBrand_ThrowException_DuplicateBrandName() {
        Long targetBrandId = 2L;
        assertThrows(ApiException.class, () -> brandService.updateBrand(targetBrandId, "D"));
    }

    @Test
    void deleteItem_success() {
        Long targetBrandId = 1L;

        brandService.deleteBrand(targetBrandId);
        assertThat(brandRepository.findById(targetBrandId)).isEmpty();
        assertThat(itemRepository.findByBrandId(targetBrandId)).hasSize(0);
    }
}
