package com.display.product.category.service;

import com.display.product.category.dto.BrandPriceDTO;
import com.display.product.category.dto.AllCategoryMinPriceResponse;
import com.display.product.category.dto.MinPriceItemDTO;
import com.display.product.category.dto.MinMaxPriceResponse;
import com.display.product.category.repository.CategoryRepository;
import com.display.product.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.display.product.category.exception.CategoryExceptionCode.CATEGORY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public AllCategoryMinPriceResponse getMINPriceByCategory() {
        List<MinPriceItemDTO> minPriceItemDTOList = categoryRepository.findCategoryMinPriceBrandInfo();
        int totalPrice = minPriceItemDTOList.stream().mapToInt(MinPriceItemDTO::getPrice).sum();
        return AllCategoryMinPriceResponse.builder().minPriceItemDTOList(minPriceItemDTOList).totalCount(totalPrice).build();
    }

    @Transactional(readOnly = true)
    public MinMaxPriceResponse getMinMaxPriceBrandByCategory(String categoryName) {
        if(categoryRepository.findByName(categoryName).isEmpty()) {
            throw new ApiException(CATEGORY_NOT_FOUND);
        }
        BrandPriceDTO minPriceBrand = categoryRepository.findPriceByCategoryName(categoryName, true);
        BrandPriceDTO maxPriceBrand = categoryRepository.findPriceByCategoryName(categoryName, false);
        return MinMaxPriceResponse.builder()
                .categoryName(categoryName)
                .minBrandPrice(minPriceBrand)
                .maxBrandPrice(maxPriceBrand)
                .build();
    }
}
