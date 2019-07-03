package com.first.test.demo.demo4;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @author chris
 **/
@Repository
public interface CurrencyPriceRepo extends JpaRepository<CurrencyPrice, Long>, Serializable {

    CurrencyPrice findBySymbol(String symbol);

}
