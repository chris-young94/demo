package com.first.test.demo.demo4.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.*;

@Data
public class HuobiData {

    private String symbol;
    private Double open;
    private Double close;
    private Double low;
    private Double high;
    private Long amount;
    private Double count;
    private Double vol;

}
