package com.cathaybk.test.DTO;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


@Data
public class ResData {
    private String name;
    private String shortName;
    private String id;
    private boolean dataGrouping;
    private List<List<BigDecimal>> data;
}
