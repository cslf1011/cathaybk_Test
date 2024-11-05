package com.cathaybk.test.service.Impl;

import com.cathaybk.test.domain.Price;
import com.cathaybk.test.repository.PriceRepository;
import com.cathaybk.test.service.PriceChangeCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class PriceChangeCalculatorServiceImpl implements PriceChangeCalculatorService {


    @Autowired
    private PriceRepository priceRepository;

    //計算漲跌
    @Override
    public String calculatePriceChange(LocalDate startDate, LocalDate endDate) {

        System.out.println(startDate);
        System.out.println(endDate);
        //找到兩個日期的分別價格
      Optional<Price>  price1 = priceRepository.findByDate(startDate);
        Optional<Price> price2= priceRepository.findByDate(endDate);
        if(price1.isEmpty()||price2.isEmpty()){
            return "沒找到日期" ;
        }
        System.out.println("p1"+price1.get().getPrice());
        System.out.println("p2"+price2.get().getPrice());

        //有找到做計算
       return  price2.get().getPrice().subtract(price1.get().getPrice()).toString();

    }

    @Override
    public String calculatePriceChangePercentage(LocalDate startDate, LocalDate endDate) {
        Optional<Price>  price1 = priceRepository.findByDate(startDate);
        BigDecimal r = new BigDecimal(calculatePriceChange(startDate,endDate));
        BigDecimal percentage= r.divide(price1.get().getPrice(),2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));

        return percentage+"%";
    }
}
