package com.cathaybk.test.controller;


import com.cathaybk.test.DTO.ProductData;
import com.cathaybk.test.service.ProductService;
import com.cathaybk.test.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
public class ProductController {


    @Autowired
    private ProductService productService;
    private final String apiUrl ="https://www.cathaybk.com.tw/cathaybk/service/newwealth/fund/chartservice.asmx/GetFundNavChart";

    public void fetchFundData(){
        //拿資料囉
        String jsonReq = "{\"req\":{\"Keys\":[\"10480016\"],\"From\":\"2023/03/10\",\"To\":\"2024/03/10\"}}";
        HttpHeaders headers =new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(jsonReq, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ProductData> response = restTemplate.postForEntity(apiUrl, entity, ProductData.class);
//        ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, entity, Map.class);
//        Map<String, Object> body = response.getBody();
        System.out.println(   response.getBody().getData().get(0).getData().get(0).get(0));
        System.out.println(DateTimeUtil.convertBigDecimalToLocalDate( response.getBody().getData().get(0).getData().get(0).get(0)));

        //存資料囉
        productService.saveInProduct( response.getBody().getData());



    }
}
