package com.example.myregistrar.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateMapper {
    public static final String PATTERN = "yyyy-MM-dd";
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(PATTERN);

    private static final Calendar CALENDAR_TODAY = Calendar.getInstance();

    public static int GET_AGE(Date date) {
        Calendar dob = Calendar.getInstance();
        dob.setTime(date);

        int age = CALENDAR_TODAY.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (CALENDAR_TODAY.get(Calendar.MONTH) < dob.get(Calendar.MONTH)
                || (CALENDAR_TODAY.get(Calendar.MONTH) == dob.get(Calendar.MONTH)
                && CALENDAR_TODAY.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH))) {
            age--;
        }

        return age;
    }
}
