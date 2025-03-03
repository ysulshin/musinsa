package com.display.product.brand.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "브랜드 생성 Request")
public class BrandCreateRequest {
    @NotNull
    @NotEmpty
    @Schema(description = "브랜드명", requiredMode = REQUIRED)
    private String name;

    @NotEmpty
    @Schema(description = "브랜드 아이템 리스트", requiredMode = REQUIRED)
    private List<BrandCreateItem> itemList;
}
