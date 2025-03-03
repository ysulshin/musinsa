package com.display.product.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "브랜드별 가격정보")
public class BrandPriceDTO {
    private String brandName;
    private Integer price;
}
