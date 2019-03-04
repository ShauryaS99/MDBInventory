package com.jackie.mdbinventory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
    }

    private void fillDummyPurchases() {
        _purchases.add(new Purchase("99 Ranch Market", "5.95",
                "Mochi flour for baking", Utils.convertToDate(2019, 3, 3)));
        _purchases.add(new Purchase("Walgreens", "50.69",
                "Swimsuits", Utils.convertToDate(2019, 3, 2)));
        _purchases.add(new Purchase("Soda Hall", "2.50",
                "My GPA", Utils.convertToDate(2019, 2, 30)));
        _purchases.add(new Purchase("Target", "30.30",
                "Baking supplies for baking social", Utils.convertToDate(2019, 2, 25)));
        _purchases.add(new Purchase("Kanyes", "10000.00",
                "SUPREME", Utils.convertToDate(2019, 1, 1)));
    }
}
