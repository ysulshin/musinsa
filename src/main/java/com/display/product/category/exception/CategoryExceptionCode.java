package com.display.product.category.exception;

import com.display.product.exception.ApiExceptionCodeBase;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
public enum CategoryExceptionCode implements ApiExceptionCodeBase {
    BRAND_NOT_FOUND(NOT_FOUND, "IT001", "존재하지 않는 브랜드입니다."),
    CATEGORY_NOT_FOUND(NOT_FOUND, "IT002", "존재하지 않는 카테고리입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    CategoryExceptionCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}

