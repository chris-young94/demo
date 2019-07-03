package com.first.test.demo.demo4;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
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

    @Resource
    private CurrencyPriceRepo currencyPriceRepo;

    @Scheduled(fixedRate = 500)
    public void getHuobiPrice() {
        long start = System.currentTimeMillis();
        String url = String.format(HUOBI_GETPRICE_URL, null);
        Map<String, Object> params = null;
        String result = OkHttpClientHelper.get(url, null, params);
        JSONObject jsonObject = JSON.parseObject(result);
        if (jsonObject == null) {
            return;
        }

        String status = jsonObject.getString("status");
        if (status.equals(STATES)) {
            String data = jsonObject.getString("data");
            List<Ticker> list = JSONObject.parseArray(data, Ticker.class);
//            List<CurrencyPrice> realTickerList = new LinkedList<>();
//            for(int j=0, m = list.size();j<m;j++) {
//                String symbol = list.get(j).getSymbol();
//                if (symbol.equals("htusdt") || symbol.equals("ethusdt") || symbol.equals("btcusdt")) {
//                    realTickerList.add(list.get(j));
//                }
//            }
//            String[] symbols = {"btcusdt","htusdt","ethusdt"};
            List<CurrencyPrice> mList = list
                    .parallelStream()
                    .filter(s -> {
                        for (SymbolEnum ignored : SymbolEnum.values()) {
                            if (ignored.getDesc().indexOf(s.getSymbol()) > -1) {
                                return true;
                            }
                        }
                        return false;
                    })
//                    .filter(s -> {
//                        for(int i=0, n=symbols.length;i<n;i++){
//                            if (s.getSymbol().equals(symbols[i])){
//                                return true;
//                            }
//                        } return false;
//                    })
                    .map(ticker -> {
                        String symbol = ticker.getSymbol();
                        BigDecimal close = BigDecimal.valueOf(ticker.getClose());
                        Long time = System.currentTimeMillis();
                        CurrencyPrice currencyPrice = currencyPriceRepo.findBySymbol(symbol);
                        if (null == currencyPrice) {
                            CurrencyPrice currencyPrice1 = new CurrencyPrice(close, symbol, time);
                            return currencyPrice1;
                        } else {
                            currencyPrice.setPrice(close);
                            currencyPrice.setCreateTime(time);
                            return currencyPrice;
                        }
                    }).collect(Collectors.toList());
            currencyPriceRepo.saveAll(mList);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }


}
