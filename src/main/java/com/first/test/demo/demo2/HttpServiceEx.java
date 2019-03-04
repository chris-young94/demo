package com.first.test.demo.demo2;


import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.web3j.protocol.http.HttpService;

import java.util.concurrent.TimeUnit;

public class HttpServiceEx extends HttpService {

    public HttpServiceEx(String serverUrl) {
        super();
    }

    private static OkHttpClient createOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        MyOkHttpRetryInterceptor myOkHttpRetryInterceptor = new MyOkHttpRetryInterceptor.Builder()
                .executionCount(5).retryInterval(1000).build();
        builder.addInterceptor(myOkHttpRetryInterceptor);
        builder.retryOnConnectionFailure(true);
        builder.connectionPool(new ConnectionPool())
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS).build();
        return builder.build();
    }


}
