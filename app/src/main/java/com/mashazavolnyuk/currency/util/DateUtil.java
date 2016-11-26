package com.mashazavolnyuk.currency.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Dark Maleficent on 26.11.2016.
 */

public class DateUtil {

    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }

    public static String getPreviousDay(Date date, int days, DateFormat dateFormat) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -days);
        return dateFormat.format(cal.getTime());
    }
}
