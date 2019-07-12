package com.first.test.demo.demo4.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chris
 * @Description 统一请求方式
 */
public class HuoBiPriceClient {

    private final static String URL = "https://api.huobi.pro/market/";
    private final static String STATES = "ok";
    private final static String GET = "GET";
    private final static String POST = "POST";

    /**
     * 获取全部行情信息
     */
    public void getAllPrice(){
        // TODO: 2019/7/8
    }



    public <T> T get(String url, Map<String, String> header, Map<String, Object> params, TypeReference<T> typeReference, String key){
        if (null == header){
            header = new HashMap<>();
        }
        if (null == params){
            params = new HashMap<>();
        }
        return call("GET",url,header,params,typeReference,key);
    }

    public <T> T post(String url, Map<String, String> header, Map<String, Object> params, TypeReference<T> typeReference, String key){
        if (null == header){
            header = new HashMap<>();
        }
        if (null == params){
            params = new HashMap<>();
        }
        return call("POST",url,header,params,typeReference,key);
    }


    /**
     *返回多种类型，使用范型解决，并且需将其转型
     */
    public <T> T call(String method, String url, Map<String, String> header, Map<String, Object> params, TypeReference<T> typeReference, String key){
        try {
            String result = null;
            if (GET.equals(method)) {
                result = OkHttpClientHelper.get(url,header,params);
            } else if (POST.equals(method)) {
                result = OkHttpClientHelper.post(url,header,params);
            }

            JSONObject jsonObject = JSON.parseObject(result);
            if (jsonObject == null) {
                return null;
            }
            String status = jsonObject.getString("status");
            if (STATES.equals(status)){
                String data = jsonObject.getString(key);
                return JSONObject.parseObject(data,typeReference);

            }
            return null;

        } catch (Exception e){
            return null;
        }

    }
}
