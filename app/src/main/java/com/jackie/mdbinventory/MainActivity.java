package com.jackie.mdbinventory;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import java.util.ArrayList;
import java.util.Date;

/** Main page for the mobile application, where all the purchases can be viewed.
 * @author: Jacqueline Zhang
 * @date: 03/03/2019
 * */

public class MainActivity extends AppCompatActivity {
    /** View-related variables. */
    private RecyclerView _rView;
    private PurchaseAdapter _adapter;

    /** Represents all the purchases made and inserted in the Inventory. */
    private ArrayList<Purchase> _purchases;

    /** SQL-related variables. */
    private InventoryDbHelper _dbHelper;
    private SQLiteDatabase _db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _purchases = new ArrayList<>();
        // fillDummyPurchases();
        _rView = findViewById(R.id.purchasesRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        _rView.setLayoutManager(layoutManager);
        _adapter = new PurchaseAdapter(this, _purchases);
        _rView.setAdapter(_adapter);

        // Sets up toolbar.
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        _dbHelper = new InventoryDbHelper(this);
        _db = _dbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                Inventory.InventoryEntry.COLUMN_MERCHANT_NAME,
                Inventory.InventoryEntry.COLUMN_DESCRIPTION,
                Inventory.InventoryEntry.COLUMN_DATE,
                Inventory.InventoryEntry.COLUMN_COST
        };


        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                Inventory.InventoryEntry.COLUMN_DATE + " DESC";

        Cursor cursor = _db.query(
                Inventory.InventoryEntry.TABLE_NAME,   // The table to query
                projection,                            // The array of columns to return (pass null to get all)
                null,                         // The columns for the WHERE clause
                null,                     // The values for the WHERE clause
                null,                         // don't group the rows
                null,                          // don't filter by row groups
                sortOrder                             // The sort order
        );

        while(cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(Inventory.InventoryEntry._ID));
            String merchant = cursor.getString(
                    cursor.getColumnIndexOrThrow(Inventory.InventoryEntry.COLUMN_MERCHANT_NAME));
            String description = cursor.getString(
                    cursor.getColumnIndexOrThrow(Inventory.InventoryEntry.COLUMN_DESCRIPTION));
            String date = cursor.getString(
                    cursor.getColumnIndexOrThrow(Inventory.InventoryEntry.COLUMN_DATE));
            Date d = Utils.convertToDate(Utils.getYearFromStr(date), Utils.getMonthFromStr(date), Utils.getDayFromStr(date));
            String cost = cursor.getString(
                    cursor.getColumnIndexOrThrow(Inventory.InventoryEntry.COLUMN_COST));
            Purchase p = new Purchase(id, merchant, description, d, cost);
            _purchases.add(p);
        }
        cursor.close();

    }


    /** Creates all the menu options for the toolbar (the add button). */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_toolbar, menu);
        return true;
    }

    /** Handles selection of options for the Drawer. */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent i = new Intent(this, AddActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        _dbHelper.close();
        super.onDestroy();
    }
}
