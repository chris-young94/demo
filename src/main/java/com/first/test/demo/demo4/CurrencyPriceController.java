package com.first.test.demo.demo4;



import com.first.test.demo.demo4.entity.CurrencyPriceVO;
import com.first.test.demo.demo4.resp.ResponseStatusEnum;
import com.first.test.demo.demo4.resp.RestResp;
import com.first.test.demo.demo4.service.CurrenyPriceSevice;
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
//    @Cacheable(cacheNames = "Ticker",key = "#currencyPriceVO['symbol']",condition = "#currencyPriceVO['symbol'] != null ")
    public WebAsyncTask<RestResp> getTicker(@RequestBody CurrencyPriceVO currencyPriceVO){
        return new WebAsyncTask<> (5000,() -> {
            if (null == currencyPriceVO.getSymbol()){
                return RestResp.fail(ResponseStatusEnum.DATA_ERROR);
            }
            return currenyPriceSevice.getTickerBySymbol(currencyPriceVO);
        });
    }
}
