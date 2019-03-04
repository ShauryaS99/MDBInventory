package com.jackie.mdbinventory;

import java.util.Calendar;
import java.util.Date;

public class Utils {

    public static Date convertToDate(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        return c.getTime();
    }
}
