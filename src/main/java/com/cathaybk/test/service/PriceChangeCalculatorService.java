package com.cathaybk.test.service;

import java.time.LocalDate;

public interface PriceChangeCalculatorService {
    String calculatePriceChange(LocalDate startDate, LocalDate endDate);
    String calculatePriceChangePercentage(LocalDate startDate, LocalDate endDate);
}

