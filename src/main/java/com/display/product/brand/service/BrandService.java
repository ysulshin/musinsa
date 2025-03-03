package com.display.product.brand.service;

import com.display.product.brand.dto.BrandCreateRequest;
import com.display.product.brand.dto.BrandCreateItem;
import com.display.product.brand.dto.CategoryPriceDTO;
import com.display.product.brand.dto.TotalMinPriceBrandResponse;
import com.display.product.brand.entity.Brand;
import com.display.product.brand.repository.BrandRepository;
import com.display.product.category.entity.Category;
import com.display.product.category.repository.CategoryRepository;
import com.display.product.exception.ApiException;
import com.display.product.item.entity.Item;
import com.display.product.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.display.product.brand.exception.BrandExceptionCode.*;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;

    @Transactional(readOnly = true)
    public TotalMinPriceBrandResponse getBrandWithMinPrice() {
        Brand brand = brandRepository.findBrandWithLowestCategoryMinPrice().orElseThrow(() -> new ApiException(NOT_EXIST_BRAND));
        List<CategoryPriceDTO> categoryPriceDTOList = brandRepository.findMinPricePerCategoryByBrandId(brand.getId());
        int totalPrice = categoryPriceDTOList.stream().mapToInt(CategoryPriceDTO::getPrice).sum();
        return TotalMinPriceBrandResponse.builder()
                .brandName(brand.getName())
                .categoryPriceList(categoryPriceDTOList)
                .totalPrice(totalPrice)
                .build();
    }
    @Transactional
    public void createBrand(BrandCreateRequest brandCreateRequest) {
        //Brand Validation
        if(brandRepository.existsByName(brandCreateRequest.getName())) {
            throw new ApiException(BRAND_NOT_FOUND);
        }

        //Category Validation
        Set<Long> categoryIds = brandCreateRequest.getItemList().stream()
                .map(BrandCreateItem::getCategoryId)
                .collect(Collectors.toSet());

        List<Category> categoryList = categoryRepository.findAll();
        Set<Long> foundCategoryIds = categoryList.stream()
                .map(Category::getId)
                .collect(Collectors.toSet());

        if(foundCategoryIds.size() != categoryIds.size() || (!foundCategoryIds.containsAll(categoryIds))) {
            throw new ApiException(NEED_ALL_CATEGORY_ITEM);
        }

        // Save Brand
        Brand brand = Brand.builder()
               .name(brandCreateRequest.getName())
               .build();
        brandRepository.save(brand);

        // Save Items
        Map<Long, Category> categoryMap = categoryList.stream().collect(Collectors.toMap(Category::getId, category -> category));
        List<Item> itemList = new ArrayList<>();
        for(BrandCreateItem brandItemRequest : brandCreateRequest.getItemList()) {
            Category category = categoryMap.get(brandItemRequest.getCategoryId());
            Item item = new Item(brandItemRequest.getPrice(),brand, category);
            itemList.add(item);
        }
        itemRepository.saveAll(itemList);
    }

    @Transactional
    public void updateBrand(Long brandId, String brandName) {
        Brand brand = brandRepository.findById(brandId).orElseThrow(() ->  new ApiException(BRAND_NOT_FOUND));
        if (brandRepository.findByName(brandName).isPresent()) {
            throw new ApiException(ALREADY_EXIST_BRAND_NAME);
        }
        brand.updateName(brandName);
        brandRepository.save(brand);
    }

    @Transactional
    public void deleteBrand(Long id) {
        brandRepository.deleteById(id);
    }
}
