package com.jackie.mdbinventory;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** The activity for adding a purchase.
 * @author: Jacqueline Zhang
 * @date: 03/05/2019
 * */

public class AddActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {

    /** Layout-related variables. */
    private EditText _merchantET;
    private EditText _descET;
    private EditText _dateET;
    private EditText _costET;
    private Button _submitBtn;

    /** SQL-related variables. */
    private InventoryDbHelper _dbHelper;
    private SQLiteDatabase _db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // Toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        // Prevents keyboard from popping up when activity launches.
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        // Initializing variables
        _merchantET = findViewById(R.id.merchantET);
        _descET = findViewById(R.id.descET);
        _dateET = findViewById(R.id.dateET);
        _costET = findViewById(R.id.costET);
        _submitBtn = findViewById(R.id.submitBtn);
        _submitBtn.setOnClickListener(this);

        // Setting listeners
        _merchantET.setOnKeyListener(this);
        _descET.setOnKeyListener(this);

        // SQL Database Insertion
        _dbHelper = new InventoryDbHelper(this);
        // Get the database. If it does not exist, this is where it will
        // also be created.
        _db = _dbHelper.getWritableDatabase();
    }

    @Override
    protected void onDestroy() {
        _dbHelper.close();
        super.onDestroy();
    }

    /** Handles all the clicks. */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submitBtn:
                String merchant = retrieveMerchant();
                String description = retrieveDesc();
                String date = retrieveDate();
                String cost = retrieveCost();
                if (merchant.equals("") || description.equals("") || date.equals("") || cost.equals("")) {
                    return;
                }
                Date d = Utils.convertToDate(Utils.getYearFromStr(date), Utils.getMonthFromStr(date), Utils.getDayFromStr(date));
                ContentValues values = Utils.insertEntries(merchant, description, date, d, cost);
                long newRowId = _db.insert(Inventory.InventoryEntry.TABLE_NAME, null, values);
                Intent i = new Intent(AddActivity.this, MainActivity.class);
                startActivity(i);
        }
    }

    /** Handles input for MERCHANTET and DESCET by limiting the number of characters the user can input. */
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        switch (v.getId()) {
            case R.id.merchantET:
                if (_merchantET.getText().length() >= 30)
                    Toast.makeText(getApplicationContext(), "Please only input up to 30 characters.",
                            Toast.LENGTH_SHORT).show();
                return false;
            case R.id.descET:
                if (_descET.getText().length() >= 80)
                    Toast.makeText(getApplicationContext(), "Please only input up to 80 characters.",
                            Toast.LENGTH_SHORT).show();
                return false;
        }
        return false;
    }

    /** Retrieves merchant name from _MERCHANTET. */
    public String retrieveMerchant() {
        // Check if there's a valid merchant name.
        String merchant = _merchantET.getText().toString();
        if (merchant == null || merchant.equals("")) {
            Toast.makeText(AddActivity.this, "Please enter a merchant.", Toast.LENGTH_LONG).show();
            return "";
        }
        return merchant;
    }

    /** Retrieves description from _DESCET. */
    public String retrieveDesc() {
        // Check if there's a valid description.
        String description = _descET.getText().toString();
        if (description == null || description.equals("")) {
            Toast.makeText(AddActivity.this, "Please enter a description.", Toast.LENGTH_LONG).show();
            return "";
        }
        return description;
    }

    /** Retrieves date from _DATEET. */
    public String retrieveDate() {
        // Check if there's a valid event date.
        String date = _dateET.getText().toString();
        if (date == null || date.equals("")) {
            Toast.makeText(AddActivity.this, "Please enter a valid date.", Toast.LENGTH_LONG).show();
            return "";
        }
        Pattern p = Pattern.compile("^(0[1-9]|1[012])/(0[1-9]|[12][0-9]|3[01])/(19|20)\\d\\d$");
        Matcher m = p.matcher(date);
        if (!m.find()) {
            Toast.makeText(AddActivity.this, "Please input a valid date in mm/dd/yyyy format.", Toast.LENGTH_LONG).show();
            return "";
        }
        return date;
    }

    /** Retrieves cost from _COSTET. */
    public String retrieveCost() {
        // Check if there's a valid cost.
        String cost = _costET.getText().toString();
        if (cost == null || cost.equals("")) {
            Toast.makeText(AddActivity.this, "Please enter a cost amount.", Toast.LENGTH_LONG).show();
            return "";
        }
        // Check if cost matches the format.
        Pattern costP = Pattern.compile("^((([1-9]\\d*)?\\d)(\\.\\d\\d)?)|$");
        Matcher costM = costP.matcher(cost);
        if (!costM.find()) {
            Toast.makeText(AddActivity.this, "Please input the cost with 2 decimal places.", Toast.LENGTH_LONG).show();
            return "";
        }
        return cost;
    }

}
