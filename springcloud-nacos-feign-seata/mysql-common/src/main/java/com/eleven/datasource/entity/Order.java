package com.eleven.datasource.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Order {
    private Integer id;
    
    private String userId;
    /** ååįžå· */
    private String commodityCode;
    
    private Integer count;
    
    private Integer money;
    
    private Integer status;
}
