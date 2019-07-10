package com.first.test.demo.demo4;


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
    private CurrenPriceSevice currenPriceSevice;


    @PostMapping("/Ticker")
    @Cacheable(cacheNames = "Ticker",key = "#result")
    public WebAsyncTask<RestResp> getTicker(@RequestBody CurrencyPriceVO currencyPriceVO){
        return new WebAsyncTask<RestResp> (5000,() -> {
            if (null == currencyPriceVO.getSymbol()){
                return RestResp.fail(ResponseStatusEnum.DATA_ERROR);
            }
            return currenPriceSevice.getTickerBySymbol(currencyPriceVO);
        });
    }
}
