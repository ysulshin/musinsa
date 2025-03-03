package com.display.product.item.service;

import com.display.product.brand.repository.BrandRepository;
import com.display.product.category.repository.CategoryRepository;
import com.display.product.exception.ApiException;
import com.display.product.item.dto.ItemCreateRequest;
import com.display.product.item.dto.ItemUpdateRequest;
import com.display.product.item.entity.Item;
import com.display.product.item.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class ItemIntegrationTest {
    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void createItem_Success() {
        ItemCreateRequest request = new ItemCreateRequest(1000, "A", "상의");
        itemService.createItem(request);

        assertThat(itemRepository.findAll()).hasSize(73);
    }

    @Test
    void createItem_ThrowException_BrandNotFound() {
        ItemCreateRequest request = new ItemCreateRequest(1000, "Z", "상의");
        assertThrows(ApiException.class, () -> itemService.createItem(request));
    }

    @Test
    void createItem_ThrowException_CategoryNotFound() {
        ItemCreateRequest request = new ItemCreateRequest(1000, "A", "테스트카테고리");
        assertThrows(ApiException.class, () -> itemService.createItem(request));
    }

    @Test
    void updateItem_Success() {
        Long targetItemId = 1L;
        ItemUpdateRequest updateRequest = new ItemUpdateRequest(15000, "상의");
        itemService.updateItem(targetItemId, updateRequest);

        Item updatedItem = itemRepository.findById(targetItemId).orElseThrow();
        assertThat(updatedItem.getPrice()).isEqualTo(15000);
    }

    @Test
    void updateItem_ThrowException_CategoryNotFound() {
        Long targetItemId = 1L;
        ItemUpdateRequest request = new ItemUpdateRequest(15000, "하의");
        assertThrows(ApiException.class, () -> itemService.updateItem(targetItemId, request));
    }

    @Test
    void updateItem_ThrowException_ItemNotFound() {
        ItemUpdateRequest request = new ItemUpdateRequest(15000, "상의");
        assertThrows(ApiException.class, () -> itemService.updateItem(999L, request));
    }

    @Test
    void updateItem_ThrowException_LastCategoryItem() {
        Long targetItemId = 1L;
        ItemUpdateRequest request = new ItemUpdateRequest(15000, "가방");
        assertThrows(ApiException.class, () -> itemService.updateItem(targetItemId, request));
    }

    @Test
    void deleteItem_ThrowException_LastCategoryItem() {
        Long targetItemId = 4L;
        assertThrows(ApiException.class, () -> itemService.deleteItem(targetItemId));
    }
}
