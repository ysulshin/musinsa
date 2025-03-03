package com.display.product.brand.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "카테고리별 가격정보")
public class CategoryPriceDTO {
    private String categoryName;
    private Integer price;
}
