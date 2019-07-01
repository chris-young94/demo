package com.first.test.demo.demo4;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.*;

@Data
public class Ticker {

    /**
     * 以基础币种计量的交易量
     */
    @Column(nullable = false)
    private float amount;

    /**
     * 交易笔数
     */
    @Column(nullable = false)
    private  Integer count;

    /**
     * 开盘价
     */
    @Column(nullable = false)
    private  float open;

    /**
     * 最新价
     */
    @Column(nullable = false)
    private float close;

    /**
     * 最低价
     */
    @Column(nullable = false)
    private float low;

    /**
     * 最高价
     */
    @Column(nullable = false)
    private float high;

    /**
     * 以报价币种计量的交易量
     */
    private float vol;

    /**
     * 交易对，例如btcusdt, ethbtc
     */
    @Column(nullable = false)
    private String symbol;

}
