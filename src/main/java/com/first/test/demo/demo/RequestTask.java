package com.first.test.demo.demo;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;


public class RequestTask implements Runnable {
    private String url;

    public RequestTask(String url) {
        super();
        this.url = url;
    }

    @Override
    public void run() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int i = 1;

        while (true) {
            String address = null;
            String filename = null;
            int num = 1;
            System.out.println("第" + i + "个账号准备生成");
            //生成钱包
            GetAdress adr = null;
            try {
                  adr = GetAdress.run1();
            }
            catch (NoSuchAlgorithmException e) { e.printStackTrace(); }
            catch (NoSuchProviderException e) { e.printStackTrace(); }
            catch (InvalidAlgorithmParameterException e) { e.printStackTrace(); }
            catch (CipherException e) { e.printStackTrace(); }
            catch (IOException e) { e.printStackTrace(); }

            address = adr.getAdr();
            filename = adr.getWalletFileName1();
            System.out.println("新钱包地址"+address+"          新钱包文件名"+filename);
            System.out.println("——————————————————————————————————————————————————————-—————————————————————————————");

            while (true) {
                try {
                    String result = null;
                    String str = "rpc error with payload";
                    int status = 0;

                    HttpClientUtils httpClientUtils = HttpClientUtils.sendPost(url, address);
                    result = httpClientUtils.getMessage();
                    status = httpClientUtils.getCode();

                    boolean sta = result.contains(str);
                    while (status == 502 || sta) {
                        if (status == 502) {
                            System.out.println("失败状态码为" + status + ",准备重新取币,等待时间15S");
                            Thread.sleep(10 * 1000); //每次间隔15秒
                        }
                        if (sta){
                            System.out.println("失败状态信息为" + status + str+",准备重新访问,等待时间1分钟      "+sdf.format(new Date()));
                            Thread.sleep(1*60 * 1000); //每次间隔60秒
                        }
                        HttpClientUtils httpClientUtils2 = HttpClientUtils.sendPost(url, address);
                        result = httpClientUtils2.getMessage();
                        status = httpClientUtils2.getCode();
                        sta = result.contains(str);
                    }

                    if (status != 200) {
                        System.out.println(sdf.format(new Date()) + "            任务失败，失败状态码=" + status);
                        System.out.println("失败信息为:" + result);
                        num--;
                        if (num <= 1){
                            System.out.println("无币转账，准备生成新钱包，等待45秒");
                            Thread.sleep(30 * 1000); //转账后等待45秒重新生成新钱包
                            i++;
                            break;
                        }
                        else {
                            //此处转账
                            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>准备转账<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                            Thread.sleep(10 * 1000); //每次间隔10秒
                            Credentials credentials = WalletUtils.loadCredentials(
                                    "12345678",
                                    "/Users/chris/Desktop/Auto/" + filename);
                            String txhash = new Transaction().transaction(num, address, credentials);
                            if (txhash.equals(null)){
                                String txhash2 = new Transaction().transaction(num,address,credentials);
                                if (txhash2.equals(null)){
                                    System.out.println("转账失败");
                                } else {
                                    System.out.println("转账成功，交易地址为"+txhash2);
                                }
                            } else {
                                System.out.println("转账成功，交易地址为"+txhash);
                            }
                            System.out.println("准备生成新钱包，等待50秒");
                            Thread.sleep(30 * 1000); //转账后等待30秒重新生成新钱包
                            i++;
                        }
                        break;

                    } else {
                        try {
                            System.out.println("-------------------------------第" + i + "个账号，第" + num + "次完成取币---------------------");
                            Thread.sleep(6 * 1000); //每次间隔6秒
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        num++;
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidAlgorithmParameterException e) {
                    e.printStackTrace();
                } catch (NoSuchProviderException e) {
                    e.printStackTrace();
                } catch (CipherException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}