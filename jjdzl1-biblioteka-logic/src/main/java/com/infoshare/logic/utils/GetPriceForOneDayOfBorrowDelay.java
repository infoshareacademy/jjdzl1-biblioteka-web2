package com.infoshare.logic.utils;

import java.io.FileNotFoundException;
import java.math.BigDecimal;


public class GetPriceForOneDayOfBorrowDelay {
    public static BigDecimal getPriceForOneDayOfBorrowDelay() throws FileNotFoundException {

        Double price = Double.parseDouble(ReadProperties.readPropertie("price-for-one-day-of-borrow-delay"));

        BigDecimal priceForOneDayOfBorrowDelay = new BigDecimal(price);

        return priceForOneDayOfBorrowDelay;
    }
}
