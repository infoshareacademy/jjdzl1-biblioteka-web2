package com.infoshare.logic.utils;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;

public class CalculateFeeToPay {

    public static BigDecimal calculateFeeToPay(LocalDate startDate, LocalDate endDate) throws FileNotFoundException {

        BigDecimal priceforOneDayOfBorrowDelay = GetPriceForOneDayOfBorrowDelay.getPriceForOneDayOfBorrowDelay();

        Integer days = getDays(endDate).getDays();

        BigDecimal payForBorrow = new BigDecimal(0.5).multiply(new BigDecimal(days)).setScale(2, RoundingMode.HALF_UP);

        return payForBorrow;
    }

    public static Period getDays(LocalDate endDate) {
        return Period.between(endDate, LocalDate.now());
    }
}
