package com.first.test.demo.demo;


import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class GetAdress {
    private final String address;
    private final String walletFileName0;

    public GetAdress(String address,String walletFileName0){
        this.address = address;
        this.walletFileName0 = walletFileName0;
    }

    public String getAdr(){
        return address;
    }

    public String getWalletFileName1(){
        return walletFileName0;
    }
    private void createWallet() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, CipherException, IOException {

        //钱包文件保持路径，请替换位自己的某文件夹路径

        //WalletUtils.generateFullNewWalletFile("password1",new File(walleFilePath1));
        //WalletUtils.generateLightNewWalletFile("password2",new File(walleFilePath2));
    }

    protected static GetAdress run1() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, CipherException, IOException {
        String walletFileName0 = "";     //文件名
        String walletFilePath0 = "/Users/chris/Desktop/Auto/"; //钱包地址
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        String address = null;
        try {
            walletFileName0 = WalletUtils.generateNewWalletFile("12345678", new File(walletFilePath0), false);
            Credentials credentials = WalletUtils.loadCredentials("12345678", "/Users/chris/Desktop/Auto/" + walletFileName0);
            address = credentials.getAddress();
            BigInteger publicKey = credentials.getEcKeyPair().getPublicKey();
            BigInteger privateKey = credentials.getEcKeyPair().getPrivateKey();
            System.out.println("———————————————————————————————————开始生成钱包——————————————————————————————————————"+sdf.format(new Date()));
            System.out.println("walletName: " + walletFileName0);
            System.out.println("address:" + address + ",publicKey:" + publicKey + ",privateKey:" + privateKey);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }

        return new GetAdress(address,walletFileName0);
    }
}
