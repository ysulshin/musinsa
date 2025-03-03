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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {
    @InjectMocks
    private ItemService itemService;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private CategoryRepository categoryRepository;

    private ItemCreateRequest itemCreateRequest;
    private ItemUpdateRequest itemUpdateRequest;
    private Brand brand;
    private Category category;
    private Item item;

    @BeforeEach
    void setUp() {
        itemCreateRequest = new ItemCreateRequest(1000, "브랜드A", "상의");
        itemUpdateRequest = new ItemUpdateRequest(2000, "상의");
        category = Category.builder().name("상의").build();
        brand = Brand.builder().name("브랜드A").build();
        item = Item.builder().id(1L).price(1000).category(category).brand(brand).build();
    }

    @Test
    void createItem_Success() {
        when(brandRepository.findByName("브랜드A")).thenReturn(Optional.of(brand));
        when(categoryRepository.findByName("상의")).thenReturn(Optional.of(category));

        itemService.createItem(itemCreateRequest);

        verify(itemRepository, times(1)).save(any(Item.class));
    }

    @Test
    void createItem_ThrowException_BrandNotFound() {
        when(brandRepository.findByName("브랜드A")).thenReturn(Optional.empty());
        assertThrows(ApiException.class, () -> itemService.createItem(itemCreateRequest));
    }

    @Test
    void createItem_ThrowException_CategoryNotFound() {
        when(brandRepository.findByName("브랜드A")).thenReturn(Optional.of(brand));
        when(categoryRepository.findByName("상의")).thenReturn(Optional.empty());
        assertThrows(ApiException.class, () -> itemService.createItem(itemCreateRequest));
    }

    @Test
    void updateItem_Success() {
        when(itemRepository.findById(item.getId())).thenReturn(Optional.of(item));
        when(categoryRepository.findByName("상의")).thenReturn(Optional.of(category));
        when(itemRepository.existsByBrandIdAndCategoryIdAndIdNot(brand.getId(), category.getId(), item.getId())).thenReturn(true);

        ArgumentCaptor<Item> captor = ArgumentCaptor.forClass(Item.class);
        itemService.updateItem(item.getId(), itemUpdateRequest);

        verify(itemRepository, times(1)).save(any(Item.class));
        verify(itemRepository).save(captor.capture());
        assertEquals(2000, captor.getValue().getPrice());
    }

    @Test
    void updateItem_ThrowException_ItemNotFound() {
        when(itemRepository.findById(item.getId())).thenReturn(Optional.empty());
        assertThrows(ApiException.class, () -> itemService.updateItem(item.getId(), itemUpdateRequest));
    }

    @Test
    void deleteItem_Success() {
        when(itemRepository.findById(item.getId())).thenReturn(Optional.of(item));
        when(itemRepository.existsByBrandIdAndCategoryIdAndIdNot(brand.getId(), category.getId(), item.getId())).thenReturn(true);

        itemService.deleteItem(item.getId());

        verify(itemRepository, times(1)).deleteById(item.getId());
    }

    @Test
    void deleteItem_ThrowException_LastItem() {
        when(itemRepository.findById(item.getId())).thenReturn(Optional.of(item));
        when(itemRepository.existsByBrandIdAndCategoryIdAndIdNot(brand.getId(), category.getId(), item.getId())).thenReturn(false);

        assertThrows(ApiException.class, () -> itemService.deleteItem(item.getId()));
    }
}
