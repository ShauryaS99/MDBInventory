package com.jackie.mdbinventory;

import android.provider.BaseColumns;

/** Represents the purchases made by MDB members.
 * @author: Jacqueline Zhang
 * @date: 03/05/2019
 * */

public final class Inventory {
    private Inventory() {}

    public static class InventoryEntry implements BaseColumns {
        public static final String TABLE_NAME = "inventory";
        public static final String COLUMN_MERCHANT_NAME = "merchant";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_COST = "cost";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + InventoryEntry.TABLE_NAME + " (" +
                        InventoryEntry._ID + " INTEGER PRIMARY KEY," +
                        InventoryEntry.COLUMN_MERCHANT_NAME + " TEXT," +
                        InventoryEntry.COLUMN_DESCRIPTION + " TEXT," +
                        InventoryEntry.COLUMN_DATE + " TEXT," +
                        InventoryEntry.COLUMN_COST + " TEXT)";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + InventoryEntry.TABLE_NAME;

    }
}

