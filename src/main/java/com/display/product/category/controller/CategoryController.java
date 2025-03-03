package com.display.product.category.controller;

import com.display.product.category.dto.AllCategoryMinPriceResponse;
import com.display.product.category.dto.MinMaxPriceResponse;
import com.display.product.category.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@Tag(name = "Category API")
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(summary = "카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 api")
    @GetMapping("/allCategoryMinPirce")
    public ResponseEntity<AllCategoryMinPriceResponse>getMinPriceBrandAndTotalByCategory() {
        return ResponseEntity.ok(categoryService.getMINPriceByCategory());
    }

    @Operation(summary = "카테고리 이름으로 최저,최고 가격 브랜드와 상품 가격을 조회하는 api")
    @GetMapping("/minmaxPriceInfo")
    public ResponseEntity<MinMaxPriceResponse> getMinMaxBrandAndPriceByCategory(@RequestParam("categoryName") String categoryName) {
        return ResponseEntity.ok(categoryService.getMinMaxPriceBrandByCategory(categoryName));
    }

}
