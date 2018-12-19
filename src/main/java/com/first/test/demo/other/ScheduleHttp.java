package com.first.test.demo.other;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.MalformedURLException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ScheduleHttp {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleHttp.class.getName());

    public static String sendGet(String url, String param) {
        String result1 = "";
        BufferedReader in1 = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);

            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();

            // 设置通用的请求属性
            connection.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
            connection.setRequestProperty("accept-encoding", "gzip, deflate, brn");
            connection.setRequestProperty("accept-language", "zh-CN,zh;q=0.9,en;q=0.8");
            connection.setRequestProperty("cache-control", "max-age=0");
            connection.setRequestProperty("user-agent",
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");

            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();

            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }

            // 定义 BufferedReader输入流来读取URL的响应
            in1 = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in1.readLine()) != null) {
                result1 += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }

        // 使用finally块来关闭输入流
        finally {
            try {
                if (in1 != null) {
                    in1.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result1;
    }


    public static String sendPost(String url, String param) throws IOException {
        logger.info("url : {}", url);
        logger.info("param : {}", param);
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {

            // 创建SSLContext对象，并使用我们指定的信任管理器初始化     
//            TrustManager[] tm = { new MyX509TrustManager() };
//            SSLContext sslContext = SSLContext.getInstance("SSL");
//            sslContext.init(null, tm, new java.security.SecureRandom());
//            //从上述SSLContext对象中得到SSLSocketFactory对象
//            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL realUrl = new URL(url);

            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();


            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("accept-encoding", "gzip, deflate, br");
            conn.setRequestProperty("content-type", "application/rawdata");
            conn.setRequestProperty("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");
            conn.setRequestProperty("accept-language", "zh-CN,zh;q=0.9,en;q=0.8");

            conn.setConnectTimeout(50 * 1000);
            // 发送POST请求,必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);

            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();

            //获取请求状态码


            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }


        } catch (MalformedURLException e) {
            logger.error("HTTP POST error : {}", e.getMessage());
        }

        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) out.close();
                if (in != null) in.close();
            } catch (IOException ex) {
                logger.error("close IO error : {}", ex.getMessage());
            }
        }
        return result.toString();
    }
}
