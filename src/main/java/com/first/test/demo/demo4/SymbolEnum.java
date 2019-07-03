package com.first.test.demo.demo4;

/**
 * @author chris
 */
public enum SymbolEnum {

    /**
     *
     */

    USDT_HT("htusdt"),
    USDT_ETH("ethusdt"),
    USDT_BTC("btcusdt");
    private String desc;

    SymbolEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc(){
        return desc;
    }
}
