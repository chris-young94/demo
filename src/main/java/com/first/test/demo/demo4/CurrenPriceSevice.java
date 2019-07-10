package com.first.test.demo.demo4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
public class CurrenPriceSevice {

    @Resource
    private CurrencyPriceRepo currencyPriceRepo;

    public RestResp getTickerBySymbol(CurrencyPriceVO currencyPriceVO){
        String symbol = currencyPriceVO.getSymbol();
        CurrencyPrice currencyPrice = currencyPriceRepo.findBySymbol(symbol);
        if (null != currencyPrice){
            return RestResp.success(currencyPrice);
        }
        return RestResp.fail();
    }
}
