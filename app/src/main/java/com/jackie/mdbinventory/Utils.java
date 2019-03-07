package com.jackie.mdbinventory;

import android.content.ContentValues;

import java.util.Calendar;
import java.util.Date;

/** Utils class.
 * @author: Jacqueline Zhang
 * @date: 03/03/2019
 * */

public class Utils {

    /** Converts YEAR, MONTH, and DAY into a Date object. */
    public static Date convertToDate(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        // Must subtract month by 1 because months start at 1 for Jan but indexing starts at 0.
        c.set(year, month - 1, day);
        return c.getTime();
    }

    /** Creates a new map of values for Inventory SQL Database.
     * @param merchant: Name of merchant.
     * @param desc: Purchase description.
     * @param date: Date of purchase.
     * @param cost: Cost of purchase.
     * @return: Returns one entry of the SQL Database. */
    public static ContentValues insertEntries(String merchant, String desc, String date, String cost) {
        ContentValues values = new ContentValues();
        values.put(Inventory.InventoryEntry.COLUMN_MERCHANT_NAME, merchant);
        values.put(Inventory.InventoryEntry.COLUMN_DESCRIPTION, desc);
        values.put(Inventory.InventoryEntry.COLUMN_DATE, date);
        values.put(Inventory.InventoryEntry.COLUMN_COST, cost);
        return values;
    }

    /** Returns the integer form of the month. */
    public static int getMonthFromStr(String s) {
        return Integer.parseInt(s.substring(0, 2));
    }

    /** Returns the integer form of the day. */
    public static int getDayFromStr(String s) {
        return Integer.parseInt(s.substring(3, 5));
    }

    /** Returns the integer form of the year. */
    public static int getYearFromStr(String s) {
        return Integer.parseInt(s.substring(6));
    }

}
