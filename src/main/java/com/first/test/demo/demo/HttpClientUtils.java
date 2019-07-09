package com.first.test.demo.demo;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class HttpClientUtils {
    private final String message;
    private final int code;

    public HttpClientUtils(String message,int code){
        this.message = message;
        this.code = code;
    }

    public String getMessage(){
        return message;
    }

    public int getCode(){
        return code;
    }
    // private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtils.class.getName());

    public static HttpClientUtils sendGet(String url, String param) throws IOException {
        System.out.println("request url info : {}" + url);
        HttpGet request = new HttpGet(url + "?" + param);
        return send(request);
    }

    public static HttpClientUtils sendPost(String url, String param) throws IOException {
        System.out.println("准备连接" + url);
        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
        HttpPost request = new HttpPost(url);
        request.setEntity(
                new StringEntity(param, "utf-8")
        );
        return send(request);
    }

    private static HttpClientUtils send(HttpRequestBase request) throws IOException {
        String message = "";

        // 设置通用的请求属性
        request.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");
        request.setHeader("accept", "*/*");
        request.setHeader("accept-encoding", "gzip, deflate, br");
        request.setHeader("accept-language", "zh-CN,zh;q=0.9,en;q=0.8");

        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = httpclient.execute(request);


        HttpEntity entity = response.getEntity();
        int code = response.getStatusLine().getStatusCode();

        if (code == 200 || code == 500) {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            if (entity != null) {
                long length = entity.getContentLength();
                if (length != -1 && length < 2048) {
                    message = EntityUtils.toString(entity);
                } else {
                    InputStream in = entity.getContent();
                    byte[] data = new byte[4096];
                    int count;
                    while ((count = in.read(data, 0, 4096)) != -1) {
                        outStream.write(data, 0, count);
                    }
                    message = new String(outStream.toByteArray(), "utf-8");
                    in.close();
                }

            }

            response.close();
            httpclient.close();
        } else {
            response.close();
            httpclient.close();
        }

        System.out.println("response message info :" + message + "           status:" + code);
        return new HttpClientUtils(message,code);
    }

}
