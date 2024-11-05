package com.cathaybk.test.service.Impl;

import com.cathaybk.test.domain.Price;
import com.cathaybk.test.repository.PriceRepository;
import com.cathaybk.test.service.PriceService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;


@Service
@Transactional
public class PriceServiceImpl  implements PriceService {

    @Autowired
    private PriceRepository priceRepository;
    @Override
    public Price findByDate(LocalDate date) {

      Optional<Price> temp = priceRepository.findByDate(date);
      if(temp.isEmpty()){
          return  null;
      }

       return temp.get();


    }

    @Override
    public int updatePriceByDate(LocalDate date,  BigDecimal newPrice) {
        if(date!=null&& newPrice!=null){
            return priceRepository.updatePriceByAndDate(newPrice,date);
        }
        return 0;
    }



    @Override
    public void addPrice(String productId, LocalDate date, BigDecimal price) {
        priceRepository.insertPrice(productId, date, price);
    }

    @Override
    public boolean existsByDate(LocalDate date) {
        return priceRepository.existsByDate(date);
    }

    @Override
    public int deleteByDate(LocalDate date) {
        return  priceRepository.deleteByDate(date);
    }


}
