package com.first.test.demo.demo4;


import com.first.test.demo.demo4.CurrencyPriceVO;
import com.first.test.demo.demo4.CurrenyPriceSevice;
import com.first.test.demo.demo4.ResponseStatusEnum;
import com.first.test.demo.demo4.RestResp;
import org.hibernate.annotations.Cache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.WebAsyncTask;

import javax.annotation.Resource;

/**
 * @author chris
 */
@RequestMapping("/CPC")
@RestController
public class CurrencyPriceController {

    @Resource
    private CurrenyPriceSevice currenyPriceSevice;


    @PostMapping("/Ticker")
    @Cacheable(cacheNames = "Ticker",key = "currencyPriceVO.symbol",condition = "#currencyPriceVO.symbol != null ")
    public WebAsyncTask<RestResp> getTicker(@RequestBody CurrencyPriceVO currencyPriceVO){
        return new WebAsyncTask<RestResp> (5000,() -> {
            if (null == currencyPriceVO.getSymbol()){
                return RestResp.fail(ResponseStatusEnum.DATA_ERROR);
            }
            return currenyPriceSevice.getTickerBySymbol(currencyPriceVO);
        });
    }
}
