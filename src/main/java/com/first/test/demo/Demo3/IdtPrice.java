package com.first.test.demo.Demo3;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.first.test.demo.demo4.DateUtil;
import com.first.test.demo.demo4.OkHttpClientHelper;

import java.util.Map;

public class IdtPrice {
    private final static String HUOBI_IDTUSDT = "https://api.huobi.pro/market/detail/merged?symbol=idteth";
    private final static String HUOBI_ETHUSDT = "https://api.huobi.pro/market/detail/merged?symbol=ethusdt";
    private final static String STATES = "ok";


    public Float getHuobiPrice() {

        String closeIdt = getClose(HUOBI_IDTUSDT);
        String closeEth = getClose(HUOBI_ETHUSDT);

        return Float.valueOf(closeIdt) * Float.valueOf(closeEth) * 7;
    }

    public String getClose(String url) {
        String format_url = String.format(url, null);
        Map<String, Object> params = null;
        String result = OkHttpClientHelper.get(format_url, null, params);
        JSONObject jsonObject = JSON.parseObject(result);
        if (jsonObject == null) {
            return null;
        }
        String status = jsonObject.getString("status");
        String close = null;
        if (status.equals(STATES)) {
            String tick = jsonObject.getString("tick");
            JSONObject jsonObject1 = JSONObject.parseObject(tick);
            close = jsonObject1.getString("close");
        }
        return close;
    }

    public static void main(String[] args) throws InterruptedException {
        IdtPrice idtPrice = new IdtPrice();
        while (true) {
            System.out.println("Idt价格为" + idtPrice.getHuobiPrice()+"                    "+ DateUtil.getPresentDate());
            System.out.println("------------------------------------------------" + "\r\n");
            Thread.sleep(1000 * 5);
        }

    }
}
