package com.cathaybk.test.service;

import com.cathaybk.test.domain.Price;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public interface PriceService {

    Price findByDate(LocalDate date);

    int updatePriceByDate(LocalDate date, BigDecimal newPrice) ;

   void addPrice(String productId, LocalDate date, BigDecimal price);

    boolean existsByDate(LocalDate date);

    int deleteByDate( LocalDate date);

}
