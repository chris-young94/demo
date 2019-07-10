package com.first.test.demo.demo4;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Component
public class HuoBiPriceSchedule {
    private final static String HUOBI_GETPRICE_URL = "https://api.huobi.pro/market/tickers";
    private final static String HUOBI_ETHUSDT = "https://api.huobi.pro/market/detail/merged?symbol=ethusdt";
    private final static String STATES = "ok";
    private final static String USDT = "usdt";
    private final static String ERC20 = "eth";
    private final static String ETHUSDT = "ethusdt";

    @Resource
    private CurrencyPriceRepo currencyPriceRepo;

    @Scheduled(fixedRate = 1000 * 60 * 5)
    public void getHuobiPrice() {
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
            BigDecimal ethPrice = new BigDecimal(getEthPrice(HUOBI_ETHUSDT));
            saveEthusdtPrice(ethPrice);
            List<CurrencyPrice> mList = list
                    .parallelStream()
                    .filter(s -> s.getSymbol().endsWith(ERC20))
                    .map(s -> {
                        try {
                            String symbol = s.getSymbol().replace("eth","usdt");

                            BigDecimal close = BigDecimal.valueOf(s.getClose());
                            BigDecimal usdtPrice = ethPrice.multiply(close);
                            Long time = System.currentTimeMillis();
                            CurrencyPrice huobidata = currencyPriceRepo.findBySymbol(symbol);
                            if (null == huobidata) {
                                 return new CurrencyPrice(usdtPrice, symbol, time);
                            } else {
                                huobidata.setPrice(usdtPrice);
                                huobidata.setCreateTime(time);
                                return huobidata;
                            }
                        } catch (Exception e) {
                            log.info("update ethusdt error");
                            return null;
                        }
                    }).collect(Collectors.toList());
            System.out.println(DateUtil.getPresentDate()+"\r\n------------------------------------------------\n\r");
            saveAll(mList);
        }
    }

    public String getEthPrice(String url) {
        String format_url = String.format(url, null);
        Map<String, Object> params = null;
        String result = OkHttpClientHelper.get(format_url, null, params);
        JSONObject jsonObject = JSON.parseObject(result);
        if (jsonObject == null) {
            return null;
        }
        String status = jsonObject.getString("status");
        String ethusdt = null;
        if (status.equals(STATES)) {
            String tick = jsonObject.getString("tick");
            JSONObject jsonObject1 = JSONObject.parseObject(tick);
            ethusdt = jsonObject1.getString("close");
        }
        return ethusdt;
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveEthusdtPrice(BigDecimal price) {
        try {
            CurrencyPrice ethUsdt = currencyPriceRepo.findBySymbol(ETHUSDT);
            if (null == ethUsdt){
                currencyPriceRepo.save(new CurrencyPrice(price,"ethusdt",System.currentTimeMillis()));
            } else {
                ethUsdt.setPrice(price);
                ethUsdt.setCreateTime(System.currentTimeMillis());
                currencyPriceRepo.save(ethUsdt);
            }
        } catch (Exception e) {
            log.info("update ethusdt error");
        }

    }

    @Transactional(rollbackFor = Exception.class)
    public void saveAll(List list) {
        try {
            currencyPriceRepo.saveAll(list);
        } catch (Exception e) {
            log.info("update token usdt price error");
        }
    }

}
