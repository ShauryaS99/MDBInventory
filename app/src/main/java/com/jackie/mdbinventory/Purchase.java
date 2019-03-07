package com.jackie.mdbinventory;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

/** Represents the purchases made by MDB members.
 * @author: Jacqueline Zhang
 * @date: 03/03/2019
 * */

public class Purchase {
    private long _id;
    private String _merchant;
    private String _cost;
    private String _description;
    private Date _date;

    public Purchase(long id, String merchant, String description, Date date, String cost) {
        _id = id;
        _merchant = merchant;
        _cost = cost;
        _description = description;
        _date = date;
    }

    public double convertCostToDouble() {
        return Double.parseDouble(_cost);
    }

    public String getMerchant() {
        return _merchant;
    }

    public String getCost() {
        NumberFormat n = NumberFormat.getCurrencyInstance(Locale.US);
        String s = n.format(Double.parseDouble(_cost));
        return s;
    }

    public String getDescription() {
        return _description;
    }

    public String getDate() {
        return _date.toString();
    }

    public long getID() {
        return _id;
    }
}
