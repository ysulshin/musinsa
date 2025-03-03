package com.display.product.brand.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@Schema(description = "전체 카테고리 최저가 정보")
public class TotalMinPriceBrandResponse {
    String brandName;
    List<CategoryPriceDTO> categoryPriceList;
    int totalPrice;
}
