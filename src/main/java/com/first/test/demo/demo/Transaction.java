package com.first.test.demo.demo;




import java.math.BigInteger;


import org.web3j.crypto.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;




public class Transaction {

    public String transaction(int a, String fromAddress, Credentials credentials) throws Exception {
        //设置需要的矿工费
        BigInteger GAS_PRICE = BigInteger.valueOf(1_000_000_000_000L);
        BigInteger GAS_LIMIT = BigInteger.valueOf(210_000);
        double b = a-0.1;

        //调用的是kovan测试环境，这里使用的是infura这个客户端
        Web3j web3j = Web3j.build(new HttpService("https://ropsten.infura.io/v3/42e18b6d9071413790b1d3b568b6d3dd"));


        //转账人账户地址
        //String ownAddress = GetAdress.run1().getAddress();

        //被转人账户地址
        String toAddress = "0x98884bFfbc885ED0381855d7580316B46D31F18b";

        //转账人私钥
        //   Credentials credentials = Credentials.create("xxxxxxxxxxxxx");

        //转账人钱包地址
//        Credentials credentials = WalletUtils.loadCredentials(
//                "12345678",
//                "/Users/chris/Desktop/Aoto/" + GetAdress.run1().getWalletFileName0());

        //getNonce
        EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(fromAddress, DefaultBlockParameterName.LATEST).send();
        BigInteger nonce = ethGetTransactionCount.getTransactionCount();


        //创建交易，number为交易ETH个数
        BigInteger value = Convert.toWei(String.valueOf(b), Convert.Unit.ETHER).toBigInteger();
        RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce, GAS_PRICE, GAS_LIMIT, toAddress, value);

//        TransactionReceipt transactionReceipt = Transfer.sendFunds(
//                web3j, credentials, toAddress,
//                BigDecimal.valueOf(0.5), Convert.Unit.ETHER).send();

        //签名Transaction，要对交易做签名
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String hexValue = Numeric.toHexString(signedMessage);

        //发送交易
        EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();

        //获得交易hash
        String transactionHash = ethSendTransaction.getTransactionHash();

        //获得到transactionHash后就可以到以太坊的网站上查询这笔交易的状态了

        return transactionHash;
    }

}