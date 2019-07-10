package com.first.test.demo.demo2;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Function;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;

import static org.web3j.protocol.core.DefaultBlockParameterName.*;


public class TransTest {
    private static Web3j web3j = null;
    private static volatile TransTest instance = null;
    private static String serverUrl = "https://mainnet.infura.io/v3/dfae09ef9e7941eebf785521b068421b";

    public Web3j getWeb3j() {
        return TransTest.web3j;
    }


    private TransTest(String serverUrl) {
        TransTest.serverUrl = serverUrl;
        web3j = Web3j.build(new HttpService(serverUrl));
    }


    public static TransTest getInstance(String serverUrl) {
        if (null == instance) {
            synchronized (TransTest.class) {
                if (null == instance) {
                    instance = new TransTest(serverUrl);
                }
            }
        }
        return instance;
    }

    public static String query(String from, String to, Function function) throws IOException {
        String encodedFunction = FunctionEncoder.encode(function);
        Transaction ethCallTransaction = Transaction.createEthCallTransaction(from, to, encodedFunction);
        Web3j web3j = TransTest.getInstance(serverUrl).getWeb3j();
        EthCall send = web3j.ethCall(ethCallTransaction, LATEST).send();
        String result = send.getResult();
        return result;
    }
}
