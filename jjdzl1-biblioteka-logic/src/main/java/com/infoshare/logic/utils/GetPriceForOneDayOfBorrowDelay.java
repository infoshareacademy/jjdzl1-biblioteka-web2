package com.infoshare.logic.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Properties;

public class GetPriceForOneDayOfBorrowDelay {
    public static BigDecimal getPriceForOneDayOfBorrowDelay() throws FileNotFoundException {

        BigDecimal priceForOneDayOfBorrowDelay;

        try {
            Properties properties = new Properties();
            InputStream inputStream = Thread.currentThread()
                    .getContextClassLoader()
                    .getResource("settings.properties")
                    .openStream();
            properties.load(inputStream);

            Double price = Double.parseDouble(properties.getProperty("price-for-one-day-of-borrow-delay"));

            priceForOneDayOfBorrowDelay = new BigDecimal(price);
        } catch (
                IOException e) {
            throw new FileNotFoundException();
        }
        return priceForOneDayOfBorrowDelay;
    }


}
