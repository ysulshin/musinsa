package com.display.product.item.controller;


import com.display.product.item.dto.ItemCreateRequest;
import com.display.product.item.dto.ItemUpdateRequest;
import com.display.product.item.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/itmes")
@RequiredArgsConstructor
@Tag(name = "Item API")
public class ItemController {
    private final ItemService itemService;

    @Operation(summary = "상품을 등록하는 api")
    @PostMapping
    public void createItem(@RequestBody ItemCreateRequest createRequest) {
        itemService.createItem(createRequest);
    }

    @Operation(summary = "상품을 변경하는 api")
    @PutMapping("/{itemId}")
    public void updateItem(@PathVariable("itemId") Long itemId, @RequestBody ItemUpdateRequest updateRequest) {
        itemService.updateItem(itemId, updateRequest);
    }

    @Operation(summary = "상품을 삭제하는 api")
    @DeleteMapping("/{itemId}")
    public void deleteItem(@PathVariable("itemId") Long brandId) {
        itemService.deleteItem(brandId);
    }
}
