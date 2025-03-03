package com.display.product.brand.controller;


import com.display.product.brand.dto.BrandCreateRequest;
import com.display.product.brand.dto.TotalMinPriceBrandResponse;
import com.display.product.brand.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Brand API")
@RequestMapping("/brand")
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;

    @Operation(summary = "단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 api")
    @GetMapping("/minPirce")
    public ResponseEntity<TotalMinPriceBrandResponse> getMinPriceBrandForAllCategories() {
        return ResponseEntity.ok(brandService.getBrandWithMinPrice());
    }

    /**
     * 운영자는 새로운 브랜드를 등록하고, 모든 브랜드의 상품을 추가,변경,삭제할 수 있어야 한다.
     */
    @Operation(summary = "브랜드를 추가하는 api")
    @PostMapping
    public void createBrand(@RequestBody BrandCreateRequest brandCreateRequest) {
        brandService.createBrand(brandCreateRequest);
    }

    @Operation(summary = "브랜드를 변경하는 api")
    @PutMapping("/{brandId}")
    public void updateBrand(@PathVariable("brandId") Long brandId, @RequestParam("name") String name) {
        brandService.updateBrand(brandId, name);
    }

    @Operation(summary = "브랜드를 삭제하는 api")
    @DeleteMapping("/{brandId}")
    public void deleteBrand(@PathVariable("brandId") Long brandId) {
        brandService.deleteBrand(brandId);
    }

}
