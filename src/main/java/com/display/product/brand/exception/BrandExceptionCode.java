package com.display.product.brand.exception;

import com.display.product.exception.ApiExceptionCodeBase;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
public enum BrandExceptionCode implements ApiExceptionCodeBase {
    BRAND_NOT_FOUND(NOT_FOUND, "BR001", "존재하지 않는 브랜드입니다."),
    CATEGORY_NOT_FOUND(NOT_FOUND, "BR002", "존재하지 않는 카테고리입니다."),
    ALREADY_EXIST_BRAND_NAME(BAD_REQUEST, "BR003", "이미 존재하는 브랜드명입니다."),
    NEED_ALL_CATEGORY_ITEM(BAD_REQUEST, "BR004", "브랜드 생성시 모든 카테고리의 상품이 필요합니다."),
    NOT_EXIST_BRAND(NOT_FOUND, "BR005", "존재하는 브랜드가 없습니다")

    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    BrandExceptionCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}

