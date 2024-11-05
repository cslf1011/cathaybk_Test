package com.cathaybk.test.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;


@Data
public class ProductData {
    private int statusCode;
    @JsonProperty("Message")
    private String Message;
    @JsonProperty("Data")
    private List<ResData> Data;



}
