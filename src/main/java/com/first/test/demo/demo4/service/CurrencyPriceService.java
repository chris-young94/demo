package com.first.test.demo.demo4.service;

import com.first.test.demo.demo4.aop.CacheRemove;
import com.first.test.demo.demo4.entity.CurrencyPrice;
import com.first.test.demo.demo4.dao.CurrencyPriceRepo;
import com.first.test.demo.demo4.entity.CurrencyPriceVO;
import com.first.test.demo.demo4.entity.HuobiData;
import com.first.test.demo.demo4.resp.RestResp;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author chris
 */
@Service
public class CurrencyPriceService {

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
    //@Cacheable(cacheNames = "Ticker",key = "#symbol",condition = "#symbol != null ")
    public CurrencyPrice getTicker(String symbol){
        return currencyPriceRepo.findBySymbol(symbol);
    }




    //可以在service这个方法上面使用Cacheable会生效，而上面方法不行
    public CurrencyPrice getPrice(String symbol){
        if (null == currencyPriceRepo.findBySymbol(symbol)){
            return null;
        }
        return currencyPriceRepo.findBySymbol(symbol);
    }
}
