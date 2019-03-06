package com.jackie.mdbinventory;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {

    /** Layout-related variables. */
    private EditText _merchantET;
    private EditText _descET;
    private EditText _dateET;
    private EditText _costET;
    private Button _submitBtn;

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

    }

    /** Handles all the clicks. */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submitBtn:
                // Check if there's a valid event name. Must declare as final in order to access in OnSuccessListener
                final String eventName = _merchantET.getText().toString();
                if (eventName == null || eventName.equals("")) {
                    Toast.makeText(AddActivity.this, "Please enter an event name.", Toast.LENGTH_LONG).show();
                    return;
                }

                // Check if there's a valid description.
                final String description = _descET.getText().toString();
                if (description == null || description.equals("")) {
                    Toast.makeText(AddActivity.this, "Please enter a description.", Toast.LENGTH_LONG).show();
                    return;
                }

                // Check if there's a valid event date.
                final String date = _dateET.getText().toString();
                if (date == null || date.equals("")) {
                    Toast.makeText(AddActivity.this, "Please enter a valid date.", Toast.LENGTH_LONG).show();
                    return;
                }
                Pattern p = Pattern.compile("^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\\d\\d$");
                Matcher m = p.matcher(date);
                if (!m.find()) {
                    Toast.makeText(AddActivity.this, "Please input the date in mm/dd/yyyy format.", Toast.LENGTH_LONG).show();
                    return;
                }

                // Check if there's a valid description.
                final String cost = _costET.getText().toString();
                if (cost == null || description.equals("")) {
                    Toast.makeText(AddActivity.this, "Please enter a cost amount.", Toast.LENGTH_LONG).show();
                    return;
                }
                Pattern costP = Pattern.compile("^((([1-9]\\d*)?\\d)(\\.\\d\\d)?)|$");
                Matcher costM = costP.matcher(cost);
                if (!costM.find()) {
                    Toast.makeText(AddActivity.this, "Please input the cost with 2 decimal places.", Toast.LENGTH_LONG).show();
                    return;
                }

        }
    }
}
