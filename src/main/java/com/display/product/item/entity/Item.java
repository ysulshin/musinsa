package com.display.product.item.entity;

import com.display.product.brand.entity.Brand;
import com.display.product.category.entity.Category;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "item")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int price;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public Item(int price, Brand brand, Category category) {
        this.price = price;
        this.brand = brand;
        this.category = category;
    }

    public void updateItem(int price, Category category) {
        this.price = price;
        this.category = category;
    }
}
