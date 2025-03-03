package com.display.product.item.service;

import com.display.product.brand.entity.Brand;
import com.display.product.brand.repository.BrandRepository;
import com.display.product.category.entity.Category;
import com.display.product.category.repository.CategoryRepository;
import com.display.product.exception.ApiException;
import com.display.product.item.dto.ItemCreateRequest;
import com.display.product.item.dto.ItemUpdateRequest;
import com.display.product.item.entity.Item;
import com.display.product.item.repository.ItemRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.display.product.item.exception.ItemExceptionCode.*;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public void createItem(ItemCreateRequest request) {
        Brand brand = brandRepository.findByName(request.getBrandName())
                .orElseThrow(() -> new ApiException(BRAND_NOT_FOUND));
        Category category = categoryRepository.findByName(request.getCategoryName())
                .orElseThrow(() -> new ApiException(CATEGORY_NOT_FOUND));

        Item item = new Item(request.getPrice(), brand, category);
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId, ItemUpdateRequest request) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new ApiException(ITEM_NOT_FOUND));
        Category category = categoryRepository.findByName(request.getCategoryName())
                .orElseThrow(() -> new ApiException(CATEGORY_NOT_FOUND));

        boolean isCategoryChanged = !item.getCategory().getName().equals(request.getCategoryName());
        boolean isSameCategoryItemExists = itemRepository.existsByBrandIdAndCategoryIdAndIdNot(item.getBrand().getId(), item.getCategory().getId(), itemId);

        if (isCategoryChanged && !isSameCategoryItemExists) {
            throw new ApiException(CANNOT_CHANGE_ITEM);
        }

        item.updateItem(request.getPrice(), category);
        itemRepository.save(item);
    }

    @Transactional
    public void deleteItem(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new ApiException(ITEM_NOT_FOUND));

        if( !itemRepository.existsByBrandIdAndCategoryIdAndIdNot(item.getBrand().getId(), item.getCategory().getId(), itemId) ) {
            throw new ApiException(CANNOT_CHANGE_ITEM);
        }
        itemRepository.deleteById(itemId);
    }
}