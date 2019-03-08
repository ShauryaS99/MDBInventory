package com.jackie.mdbinventory;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

/** Represents the purchases made by MDB members.
 * @author: Jacqueline Zhang
 * @date: 03/03/2019
 * */

public class Purchase implements Serializable {
    /** Represents the ID associated with SQL Database. */
    private long _id;

    /** Represents the name of the merchant. */
    private String _merchant;

    /** Represents the cost amount. */
    private String _cost;

    /** Represents the description. */
    private String _description;

    /** Represents the date. */
    private Date _date;

    public Purchase(long id, String merchant, String description, Date date, String cost) {
        _id = id;
        _merchant = merchant;
        _cost = cost;
        _description = description;
        _date = date;
    }

    /** Returns the merchant name. */
    public String getMerchant() {
        return _merchant;
    }

    /** Returns the cost amount. */
    public String getCost() {
        // Formats the cost amount into money format.
        NumberFormat n = NumberFormat.getCurrencyInstance(Locale.US);
        String s = n.format(Double.parseDouble(_cost));
        return s;
    }

    /** Returns the description. */
    public String getDescription() {
        return _description;
    }

    /** Returns the date. */
    public String getDate() {
        return _date.toString();
    }

    /** Returns the SQL Database ID. */
    public long getID() {
        return _id;
    }
}
