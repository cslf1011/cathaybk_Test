package com.cathaybk.test.service.Impl;

import com.cathaybk.test.DTO.ResData;
import com.cathaybk.test.domain.Price;
import com.cathaybk.test.domain.Product;
import com.cathaybk.test.repository.PriceRepository;
import com.cathaybk.test.repository.ProductRepository;
import com.cathaybk.test.service.ProductService;
import com.cathaybk.test.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
public class ProductServiceImpl  implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PriceRepository  priceRepository;

    //for 得到的數據
    public void saveInProduct(List<ResData> resDataList){
        for (ResData resData : resDataList) {

            Product product = new Product();

            product.setProductId(resData.getId());
            product.setProductName(resData.getName());
            product.setShortName(resData.getShortName());
            product.setDataGrouping(resData.isDataGrouping());
            System.out.println(product.toString());
            productRepository.save(product); // 保存到資料庫


            //日期 價格
            List<List<BigDecimal>>dateAndPrice=resData.getData();
            for (List<BigDecimal> e : dateAndPrice){
                Price price = new Price();
                price.setProductId(resData.getId());
                price.setDate(DateTimeUtil.convertBigDecimalToLocalDate(e.get(0)));// 確保日期格式正確
                price.setPrice(e.get(1));
                try {
                    System.out.println(price.toString());
                    priceRepository.save(price); // 儲存 Price到資料庫
                } catch (Exception ex) {
                    System.err.println("Error saving price: " + ex.getMessage());
                }

            }

        }

    }

}
