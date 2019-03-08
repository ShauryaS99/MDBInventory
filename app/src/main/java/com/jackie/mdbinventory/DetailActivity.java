package com.jackie.mdbinventory;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

/** Represents the detailed page of a purchase.
 * @author: Jacqueline Zhang
 * @date: 03/07/2019 */

public class DetailActivity extends AppCompatActivity {
    /** Purchase being shown. */
    private Purchase _p;

    /** Layout-related variables. */
    private TextView _merchant;
    private TextView _desc;
    private TextView _date;
    private TextView _cost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Sets up toolbar.
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        // Gets the purchase that we are focusing on.
        _p = (Purchase) getIntent().getSerializableExtra("Purchase");

        // Initializes all the layout-related variables.
        _merchant = findViewById(R.id.merchDetail);
        _desc = findViewById(R.id.descDetail);
        _date = findViewById(R.id.dateDetail);
        _cost = findViewById(R.id.costDetail);

        // Populate the information.
        setUpDetails();
    }

    /** Sets up all the details for the layout. */
    void setUpDetails() {
        _merchant.setText(_p.getMerchant());
        _desc.setText(_p.getDescription());
        _date.setText(_p.getDate());
        _cost.setText(_p.getCost());
    }
}
