package com.first.test.demo.demo4;


import com.first.test.demo.demo4.aop.CacheRemove;
import com.first.test.demo.demo4.entity.CurrencyPrice;
import com.first.test.demo.demo4.entity.CurrencyPriceVO;
import com.first.test.demo.demo4.entity.HuobiData;
import com.first.test.demo.demo4.resp.GlobalConstant;
import com.first.test.demo.demo4.resp.RestResp;
import com.first.test.demo.demo4.service.CurrencyPriceService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.WebAsyncTask;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author chris
 */
@RequestMapping("/CPC")
@RestController
public class CurrencyPriceController {

    @Resource
    private CurrencyPriceService currencyPriceService;


    @PostMapping("/Ticker")
    @CacheRemove(value = "Ticker",key = "idtusdt")
//    @Cacheable(cacheNames = "Ticker",key = "#currencyPriceVO['symbol']",condition = "#currencyPriceVO['symbol'] != null ")，此时只能缓存webAsyncTask
    public WebAsyncTask<RestResp> getTicker(@RequestBody CurrencyPriceVO currencyPriceVO) {
        return new WebAsyncTask<>(5000, () -> {
            if (null == currencyPriceVO.getSymbol()) {
                return RestResp.fail(GlobalConstant.RestResponseEnum.DATA_ERROR.getDesc());
            }
            return currencyPriceService.getTickerBySymbol(currencyPriceVO);
        });
    }


    //这样缓存可以，也可以放在service层 但是service层不能调用中间方法的Cacheable方法
    @Cacheable(cacheNames = "Ticker",key = "#symbol",condition = "#symbol != null ")
    @GetMapping("/Ticker/{symbol}")
    public CurrencyPrice getPrice(@PathVariable @NotNull String symbol){
        return currencyPriceService.getPrice(symbol);
    }

}
