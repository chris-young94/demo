package com.first.test.demo.demo4;

import lombok.Data;

import javax.persistence.*;

/**
 * @author chris
 */
@Data
@Entity
@Table(name = "real_ticker")
public class RealTicker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
     * 交易对，例如btcusdt, ethbtc
     */
    @Column(nullable = false)
    private String symbol;

    /**
     * 更新时间
     */
    @Column(nullable = false)
    private Long updateTime;

    public RealTicker(float open,float close,float low,float high,String symbol,Long updateTime){
        this.open = open;
        this.close = close;
        this.low = low;
        this.high = high;
        this.symbol = symbol;
        this.updateTime = updateTime;
    }

    public RealTicker() {
    }
}
