package com.infoshare.logic.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.Properties;

public class CalculateFeeToPay {

    public static BigDecimal calculateFeeToPay(LocalDate startDate, LocalDate endDate) throws FileNotFoundException {

        Double oneDayPay;

        try {
            Properties properties = new Properties();
            InputStream inputStream = Thread.currentThread()
                    .getContextClassLoader()
                    .getResource("settings.properties")
                    .openStream();
            properties.load(inputStream);
            oneDayPay = Double.parseDouble(properties.getProperty("price-for-one-day-of-borrow-delay"));
        } catch (
                IOException e) {
            throw new FileNotFoundException();
        }

        BigDecimal priceforOneDayOfBorrowDelay = GetPriceForOneDayOfBorrowDelay.getPriceForOneDayOfBorrowDelay();

        Integer days = getDays(endDate).getDays();

        BigDecimal payForBorrow = new BigDecimal(oneDayPay).multiply(new BigDecimal(days)).setScale(2, RoundingMode.HALF_UP);

        return payForBorrow;
    }

    public static Period getDays(LocalDate endDate) {
        return Period.between(endDate, LocalDate.now());
    }
}
