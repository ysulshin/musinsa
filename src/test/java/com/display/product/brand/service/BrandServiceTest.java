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
import com.display.product.item.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BrandServiceTest {

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private BrandService brandService;

    private Brand brand;
    private Category category;
    private Category category2;
    private List<CategoryPriceDTO> categoryPriceDTOList;

    @BeforeEach
    void setUp() {
        brand = Brand.builder().id(1L).name("TestBrand").build();
        category = new Category(1L, "상의");
        category2 = new Category(2L, "하의");
        categoryPriceDTOList = List.of(new CategoryPriceDTO("상의", 1000));
    }

    @Test
    void getBrandWithMinPrice_Success() {
        when(brandRepository.findBrandWithLowestCategoryMinPrice()).thenReturn(Optional.of(brand));
        when(brandRepository.findMinPricePerCategoryByBrandId(brand.getId())).thenReturn(categoryPriceDTOList);

        TotalMinPriceBrandResponse response = brandService.getBrandWithMinPrice();

        assertNotNull(response);
        assertEquals("TestBrand", response.getBrandName());
        assertEquals(1000, response.getTotalPrice());
        verify(brandRepository, times(1)).findBrandWithLowestCategoryMinPrice();
    }

    @Test
    void getBrandWithMinPrice_ThrowException_BrandNotFound() {
        when(brandRepository.findBrandWithLowestCategoryMinPrice()).thenReturn(Optional.empty());

        assertThrows(ApiException.class, () -> brandService.getBrandWithMinPrice());
    }

    @Test
    void createBrand_Success() {
        BrandCreateRequest request = new BrandCreateRequest("NewBrand", List.of(new BrandCreateItem(1L, 2000)));

        when(brandRepository.existsByName(request.getName())).thenReturn(false);
        when(categoryRepository.findAll()).thenReturn(List.of(category));
        when(brandRepository.save(any(Brand.class))).thenReturn(brand);

        brandService.createBrand(request);

        verify(brandRepository, times(1)).save(any(Brand.class));
        verify(itemRepository, times(1)).saveAll(anyList());
    }

    @Test
    void createBrand_ThrowException_BrandAlreadyExists() {
        BrandCreateRequest request = new BrandCreateRequest("NewBrand", List.of(new BrandCreateItem(1L, 2000)));
        when(brandRepository.existsByName(request.getName())).thenReturn(true);

        assertThrows(ApiException.class, () -> brandService.createBrand(request));
    }

    @Test
    void createBrand_ThrowException_CategoryInvalid() {
        BrandCreateRequest request = new BrandCreateRequest("NewBrand", List.of(new BrandCreateItem(1L, 2000)));
        when(brandRepository.existsByName(request.getName())).thenReturn(false);
        when(categoryRepository.findAll()).thenReturn(List.of(category, category2));

        assertThrows(ApiException.class, () -> brandService.createBrand(request));
    }

    @Test
    void updateBrand_Success() {
        when(brandRepository.findById(1L)).thenReturn(Optional.of(brand));

        brandService.updateBrand(1L, "UpdatedBrand");

        assertEquals("UpdatedBrand", brand.getName());
        verify(brandRepository, times(1)).save(brand);
    }

    @Test
    void updateBrand_ThrowException_BrandNotFound() {
        when(brandRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ApiException.class, () -> brandService.updateBrand(1L, "UpdatedBrand"));
    }

    @Test
    void deleteBrand_Success() {
        doNothing().when(brandRepository).deleteById(1L);

        brandService.deleteBrand(1L);

        verify(brandRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteBrand_ThrowException_BrandNotFound() {
        doThrow(EmptyResultDataAccessException.class).when(brandRepository).deleteById(1L);

        assertThrows(EmptyResultDataAccessException.class, () -> brandService.deleteBrand(1L));
    }
}
