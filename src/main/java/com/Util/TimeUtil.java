package com.Util;

import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

    // function to get begin and last time of the day from given date, date is miliseconds
    public static Long[] getBeginAndLastTimeDate(Long milliseconds) {
        Long[] result = new Long[2];
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(milliseconds)); // set date from milliseconds

        calendar.set(Calendar.HOUR_OF_DAY, 0); // set begin time of day
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        result[0] = calendar.getTimeInMillis();

        calendar.set(Calendar.HOUR_OF_DAY, 23); // set lastt time of day
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        result[1] = calendar.getTimeInMillis();
        return result;
    }
}
