package com.display.product.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "카테고리의 최대 최소 브랜드 정보")
public class MinMaxPriceResponse {
    String categoryName;
    BrandPriceDTO minBrandPrice;
    BrandPriceDTO maxBrandPrice;
}
