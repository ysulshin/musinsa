package com.display.product.brand.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "브랜드에 들어갈 상품 정보")
public class BrandCreateItem {
    @NotNull
    @Schema(description = "카테고리 아이디", requiredMode = REQUIRED)
    private Long categoryId;

    @Min(0)
    @Schema(description = "가격", requiredMode = REQUIRED)
    private int price;
}
