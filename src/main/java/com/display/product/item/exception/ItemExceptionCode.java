package com.display.product.item.exception;

import com.display.product.exception.ApiExceptionCodeBase;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.text.MessageFormat;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
public enum ItemExceptionCode implements ApiExceptionCodeBase {
    BRAND_NOT_FOUND(NOT_FOUND, "IT001", "존재하지 않는 브랜드입니다."),
    CATEGORY_NOT_FOUND(NOT_FOUND, "IT002", "존재하지 않는 카테고리입니다."),
    ITEM_NOT_FOUND(NOT_FOUND, "IT003", "존재하지 않는 카테고리입니다."),
    CANNOT_CHANGE_ITEM(BAD_REQUEST, "IT004", "카테고리 마지막 상품입니다.")
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ItemExceptionCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
    public String getFormattedMessage(Object... args) {
        return MessageFormat.format(this.message, args);
    }
}

