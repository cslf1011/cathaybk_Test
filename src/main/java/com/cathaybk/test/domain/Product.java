package com.cathaybk.test.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Product")
@Data
public class Product {
    @Id
    @Column(name = "product_id")
    private String productId;  // 商品 ID

    @Column(name = "product_name")
    private String productName; // 商品名稱

    @Column(name = "short_name")
    private String shortName;   // 商品短名稱

    @Column(name = "is_grouped")
    private boolean dataGrouping; // 資料是否分組

}
