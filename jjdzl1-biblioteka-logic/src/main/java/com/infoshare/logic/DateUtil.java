package com.infoshare.logic;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static LocalDate currentDate() {

        return LocalDate.now();
    }

    public static LocalDate currentPlusThreeDays() {
        LocalDate now = LocalDate.now();
        LocalDate plusThreedays = now.plusDays(3);
        return plusThreedays;
    }

    public static LocalDate emptyDate() {
        return LocalDate.of(1970, 01, 01);
    }

}
