package com.display.product.item.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "싱픔 추가 요청 Reqeust")
public class ItemCreateRequest {
    private int price;
    private String brandName;
    private String categoryName;
}
