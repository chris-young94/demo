package com.first.test.demo.demo4;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
    private final static String USDT = "usdt";
    private final static String ERC20 = "eth";

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
            List<HuobiData> list = JSONObject.parseArray(data, HuobiData.class);
            List<CurrencyPrice> mList = list
                    .parallelStream()
//                    .filter(s -> {
//                        for (SymbolEnum ignored : SymbolEnum.values()) {
//                            if (ignored.getDesc().indexOf(s.getSymbol()) > -1) {
//                                return true;
//                            }
//                        }
//                        return false;
//                    })
                    .filter(s -> s.getSymbol().contains(USDT))
                    .map(huobiData -> {
                        try {
                            String symbol = huobiData.getSymbol();
                            BigDecimal close = BigDecimal.valueOf(huobiData.getClose());
                            Long time = System.currentTimeMillis();
                            CurrencyPrice huobidata = currencyPriceRepo.findBySymbol(symbol);
                            if (null == huobiData) {
                                CurrencyPrice currencyPrice = new CurrencyPrice(close, symbol, time);
                                return currencyPrice;
                            } else {
                                huobidata.setPrice(close);
                                huobidata.setCreateTime(time);
                                return huobidata;
                            }
                        } catch (Exception e) {
                            return null;
                        }
                    }).collect(Collectors.toList());
            System.out.println("mList.size()长度为：" + mList.size());
            saveAll(mList);
        }
        long end = System.currentTimeMillis();
        long time = end - start;
        System.out.println("耗费时常为" + time);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveAll(List list) {
        try {
            currencyPriceRepo.saveAll(list);
        } catch (Exception e) {

        }

    }

}
