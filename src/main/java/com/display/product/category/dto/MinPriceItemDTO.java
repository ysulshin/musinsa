package com.display.product.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "최소값 상품 정보")
public class MinPriceItemDTO {
    private Long categoryId;
    private String categoryName;
    private Long brandId;
    private String brandName;
    private Integer price;
}
