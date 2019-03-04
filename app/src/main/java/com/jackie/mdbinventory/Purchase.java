/** Represents the purchases made by MDB members.
 * @author: Jacqueline Zhang
 * @date: 03/03/2019
 * */
package com.jackie.mdbinventory;

import java.util.Date;

public class Purchase {
    private String _merchant;
    private String _cost;
    private String _description;
    private Date _date;

    public Purchase(String merchant, String cost, String description, Date date) {
        _merchant = merchant;
        _cost = "- $" + cost;
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
        return _cost;
    }

    public String getDescription() {
        return _description;
    }

    // Might want to reconsider what format to return for this
    public String getDate() {
        return _date.toString();
    }
}
