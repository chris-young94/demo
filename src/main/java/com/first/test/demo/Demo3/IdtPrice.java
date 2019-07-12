package com.first.test.demo.Demo3;


import com.first.test.demo.demo4.util.DateUtil;
import com.first.test.demo.demo4.listen.HuoBiPriceSchedule;


public class IdtPrice {
    private final static String HUOBI_IDTUSDT = "https://api.huobi.pro/market/detail/merged?symbol=idteth";
    private final static String HUOBI_ETHUSDT = "https://api.huobi.pro/market/detail/merged?symbol=ethusdt";



    public Float getHuobiPrice() {

        HuoBiPriceSchedule huoBiPriceSchedule = new HuoBiPriceSchedule();
        String closeIdt = huoBiPriceSchedule.getEthPrice(HUOBI_IDTUSDT);
        String closeEth = huoBiPriceSchedule.getEthPrice(HUOBI_ETHUSDT);
        return Float.valueOf(closeIdt) * Float.valueOf(closeEth) * 7;
    }



    public static void main(String[] args) throws InterruptedException {
        IdtPrice idtPrice = new IdtPrice();
        while (true) {
            System.out.println("Idt价格为" + idtPrice.getHuobiPrice()+"                    "+ DateUtil.getPresentDate());
            System.out.println("------------------------------------------------" + "\r\n");
            Thread.sleep(1000 * 5);
        }

    }
}
