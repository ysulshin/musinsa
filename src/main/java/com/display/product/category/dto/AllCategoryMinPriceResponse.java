package com.display.product.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@Schema(description = "모든 카테고리의 최저값 정보")
public class AllCategoryMinPriceResponse {
    List<MinPriceItemDTO> minPriceItemDTOList;
    int totalCount;
}
