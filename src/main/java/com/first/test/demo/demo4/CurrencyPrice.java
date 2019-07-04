package com.first.test.demo.demo4;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author chris
 */
@Data
@Entity
@Table(name = "currency_price")
public class CurrencyPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 最新价
     */
    @Column(precision = 20, scale = 8)
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
