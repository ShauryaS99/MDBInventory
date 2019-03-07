package com.jackie.mdbinventory;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/** Utils class.
 * @author: Jacqueline Zhang
 * @date: 03/03/2019
 * */

public class Utils {
    /** Writes to SharedPreference to store the most recent search query CONSTRAINT. */
    public static void writeToSharedPref(Context c, CharSequence constraint) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("prev", constraint.toString().toLowerCase().trim());
        editor.apply();
    }

    /** Writes to SharedPreference to reset the query. */
    public static void resetQuery(Context c) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("prev", null);
        editor.apply();
    }

    /** Retrieves the most recent query from SharedPreference. */
    public static String retrievePrevQuery(Context c) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(c);
        String prevFilter = sharedPref.getString("prev", null);
        return prevFilter;
    }

    /** Updates the layout view based on whether there are items in the RecyclerView.
     * @param v: View that uses the layouts.
     * @param arr: Array used to check whether there are items to display. */
    public static <T> void updateView(View v, ArrayList<T> arr) {
        ConstraintLayout _hasItemsLayout = v.findViewById(R.id.containsItems);
        ConstraintLayout _noItemsLayout = v.findViewById(R.id.containsNoItems);
        if (arr == null || arr.size() == 0) {
            // Displays a layout that encourages the user to add items.
            _noItemsLayout.bringToFront();
        } else {
            // Displays the items.
            _hasItemsLayout.bringToFront();
        }
    }

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
    public static ContentValues insertEntries(String merchant, String desc, String date, Date d, String cost) {
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
