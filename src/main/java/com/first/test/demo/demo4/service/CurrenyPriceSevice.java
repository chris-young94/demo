package com.first.test.demo.demo4.service;

import com.first.test.demo.demo4.entity.CurrencyPrice;
import com.first.test.demo.demo4.dao.CurrencyPriceRepo;
import com.first.test.demo.demo4.entity.CurrencyPriceVO;
import com.first.test.demo.demo4.resp.RestResp;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CurrenyPriceSevice {

    @Resource
    private CurrencyPriceRepo currencyPriceRepo;

    public RestResp getTickerBySymbol(CurrencyPriceVO currencyPriceVO){
        String symbol = currencyPriceVO.getSymbol();
        CurrencyPrice currencyPrice = getTicker(symbol);
        if (null != currencyPrice){
            return RestResp.success(currencyPrice);
        }
        return RestResp.fail();
    }


    //调用中间方法并不会缓存

    @Cacheable(cacheNames = "Ticker",key = "#symbol",condition = "#symbol != null ")
    public CurrencyPrice getTicker(String symbol){
        return currencyPriceRepo.findBySymbol(symbol);
    }
}
