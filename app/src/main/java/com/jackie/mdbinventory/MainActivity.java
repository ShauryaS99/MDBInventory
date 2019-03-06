package com.jackie.mdbinventory;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView _rView;
    private ArrayList<Purchase> _purchases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _purchases = new ArrayList<>();
        fillDummyPurchases();
        _rView = findViewById(R.id.purchasesRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        _rView.setLayoutManager(layoutManager);
        PurchaseAdapter adapter = new PurchaseAdapter(this, _purchases);
        _rView.setAdapter(adapter);

        // Sets up toolbar.
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
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

    private void fillDummyPurchases() {
        _purchases.add(new Purchase("99 Ranch Market", "5.95",
                "Mochi flour for baking", Utils.convertToDate(2019, 2, 3)));
        _purchases.add(new Purchase("Walgreens", "50.69",
                "Swimsuits", Utils.convertToDate(2019, 2, 2)));
        _purchases.add(new Purchase("Soda Hall", "2.50",
                "My GPA", Utils.convertToDate(2019, 1, 30)));
        _purchases.add(new Purchase("Target", "30.30",
                "Baking supplies for baking social", Utils.convertToDate(2019, 1, 25)));
        _purchases.add(new Purchase("Kanyes", "10000.00",
                "SUPREME", Utils.convertToDate(2019, 0, 1)));
    }
}
