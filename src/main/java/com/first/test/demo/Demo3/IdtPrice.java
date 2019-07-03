package com.first.test.demo.Demo3;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.first.test.demo.demo4.OkHttpClientHelper;
import java.util.Map;

public class IdtPrice {
    private final static String HUOBI_IDTUSDT = "https://api.huobi.pro/market/detail/merged?symbol=idteth";
    private final static String HUOBI_ETHUSDT = "https://api.huobi.pro/market/detail/merged?symbol=ethusdt";
    private final static String STATES = "ok";


    public Float getHuobiPrice() {
        String url = String.format(HUOBI_IDTUSDT, null);
        String url2 = String.format(HUOBI_ETHUSDT,null);
        Map<String, Object> params = null;
        String result = OkHttpClientHelper.get(url, null, params);
        String result2 =OkHttpClientHelper.get(url2,null,params);
        JSONObject jsonObject = JSON.parseObject(result);
        JSONObject jsonObject2 = JSON.parseObject(result2);
        if (jsonObject == null || jsonObject2 == null) {
            return null;
        }

        String closeIdt = getClose(jsonObject);
        String closeEth = getClose(jsonObject2);

        return Float.valueOf(closeIdt)*Float.valueOf(closeEth)*7;
    }

    public String getClose(JSONObject jsonObject){
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
        while (true){
            System.out.println("Idt价格为"+  idtPrice.getHuobiPrice());
            System.out.println("------------------------------------------------"+"\r\n");
            Thread.sleep(1000*10);
        }

    }
}
