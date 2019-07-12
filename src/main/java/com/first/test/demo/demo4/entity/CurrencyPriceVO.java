package com.first.test.demo.demo4.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CurrencyPriceVO implements Serializable {
    private String symbol;
}
