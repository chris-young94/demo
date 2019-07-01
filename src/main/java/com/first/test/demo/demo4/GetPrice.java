package com.first.test.demo.demo4;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author chris
 */
@Component
public class GetPrice {
    private final static String HUOBI_GETPRICE_URL = "https://api.huobi.pro/market/tickers";
    private final static String STATES = "ok";
    private final static String USDT = "usdt";

    @Resource
    private RealTickerRepo realTickerRepo;

    @Scheduled(fixedRate = 1000 * 1)
    public void getHuobiPrice(){
        String url = String.format(HUOBI_GETPRICE_URL,null);
        Map<String,Object> params = null;
        String result = OkHttpClientHelper.get(url,null,params);
        JSONObject jsonObject = JSON.parseObject(result);
        if (jsonObject == null){
            return;
        }

        String  status = jsonObject.getString("status");
        if(STATES.equals(status)) {
            String b = jsonObject.getString("data");
            List<Ticker> list = JSONObject.parseArray(b,  Ticker.class);
            List<RealTicker> mList = list
                    .stream()
                    .filter(s -> s.getSymbol().contains(USDT))
                    .map(ticker -> {
                        String symbol = ticker.getSymbol();
                        float open = ticker.getOpen();
                        float low = ticker.getLow();
                        float close = ticker.getClose();
                        float high = ticker.getHigh();
                        Long time = System.currentTimeMillis();

                        RealTicker realTicker =  realTickerRepo.findBySymbol(symbol);
                        if (null == realTicker){
                            RealTicker realTicker1 =  new RealTicker(open,close,low,high,symbol,time);
                           return realTicker1;
                        }
                        else {
                           realTicker.setOpen(open);
                           realTicker.setClose(close);
                           realTicker.setHigh(high);
                           realTicker.setUpdateTime(time);
                           realTicker.setLow(low);
                           return realTicker;
                        }
                    }).collect(Collectors.toList());
            realTickerRepo.saveAll(mList);
            System.out.println(mList.size());
            System.out.println(mList);
        }
    }


}
