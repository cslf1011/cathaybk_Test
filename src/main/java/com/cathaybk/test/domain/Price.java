package com.cathaybk.test.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "price")
@Data
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "price", precision = 10, scale = 5) // DECIMAL(10, 5)
    private BigDecimal price;

}