package com.first.test.demo.demo4.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author chris
 */
@Data
@Entity
@Table(name = "currency_price")
public class CurrencyPrice implements Serializable {

    private static final long serialVersionUID = -8196494612393876134L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 最新价
     */
    @Column(precision = 10, scale = 6)
    private BigDecimal price;


    /**
     * 交易对，例如btcusdt, ethbtc
     */
    @Column(nullable = false)
    private String symbol;

    /**
     * 更新时间
     */
    @Column(nullable = false)
    private Long createTime;

    public CurrencyPrice(BigDecimal price, String symbol, Long createTime) {
        this.price = price;
        this.symbol = symbol;
        this.createTime = createTime;
    }

    public CurrencyPrice() {
    }
}
