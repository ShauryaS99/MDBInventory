package com.jackie.mdbinventory;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/** Adapter for RecyclerView displaying Purchases made.
 * @author: Jacqueline Zhang
 * @date: 03/03/2019 */

public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.PurchaseViewHolder> implements Filterable {
    // Activity-related variables.
    private Context _context;
    private View _view;

    // Represents the purchases to be used for RecyclerView.
    private ArrayList<Purchase> _purchases;
    private ArrayList<Purchase> _filteredPurchases;

    // SQL-Database variables.
    private InventoryDbHelper _dbHelper;
    private SQLiteDatabase _db;

    public PurchaseAdapter(Context context, ArrayList<Purchase> purchases, View v) {
        _context = context;
        _view = v;
        _filteredPurchases = purchases;
        _purchases = new ArrayList<>(purchases);

        // SQL Database Insertion
        _dbHelper = new InventoryDbHelper(context);
        // Get the database. If it does not exist, this is where it will
        // also be created.
        _db = _dbHelper.getWritableDatabase();

    }

    @NonNull
    @Override
    public PurchaseAdapter.PurchaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(_context).inflate(R.layout.purchase_view, viewGroup, false);
        Utils.updateView(_view.findViewById(R.id.mainLayout), _filteredPurchases);
        return new PurchaseViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseAdapter.PurchaseViewHolder purchaseViewHolder, int i) {
        purchaseViewHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        return _filteredPurchases.size();
    }

    @Override
    public Filter getFilter() {
        return searchFilter;
    }

    /** Creates a search filter based on the search query. */
    private Filter searchFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Purchase> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(_purchases);
            } else {
                String prefix = constraint.toString().toLowerCase().trim();
                for (Purchase p : _purchases) {
                    if (p.getDescription().toLowerCase().startsWith(prefix) || p.getDescription().startsWith(prefix)) {
                        filteredList.add(p);
                    }
                }
                Utils.writeToSharedPref(_context, constraint);
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        /** Updates RecyclerView with filtered results. */
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            _filteredPurchases.clear();
            _filteredPurchases.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    class PurchaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView _merchant;
        private TextView _description;
        private TextView _date;
        private TextView _cost;
        private ImageButton _deletePurchase;

        public PurchaseViewHolder(@NonNull View itemView) {
            super(itemView);
            _merchant = itemView.findViewById(R.id.merchantTextView);
            _description = itemView.findViewById(R.id.descTextView);
            _date = itemView.findViewById(R.id.dateTextView);
            _cost = itemView.findViewById(R.id.costTextView);
            _deletePurchase = itemView.findViewById(R.id.deletePurchase);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    Purchase clickedPurchase = _purchases.get(getAdapterPosition());
                    intent.putExtra("Purchase", clickedPurchase);
                    v.getContext().startActivity(intent);
                    context.startActivity(intent);
                }
            });
        }

        public void bind(int position) {
            Purchase p = _filteredPurchases.get(position);
            _merchant.setText(p.getMerchant());
            _description.setText(p.getDescription());
            _date.setText(p.getDate().substring(0, 10) + ", " + p.getDate().substring(24));
            _cost.setText(p.getCost());
            _deletePurchase.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.deletePurchase:
                    // remove the item from recycler view
                    removeItem(getAdapterPosition());
            }
        }

        /** Handles removing a purchase at POSITION.
         * Removes it from the SQL Database and the RecyclerView. */
        public void removeItem(int position) {
            Purchase p = _filteredPurchases.get(position);
            _purchases.remove(position);
            _filteredPurchases.remove(position);
            String whereClause = "_id=?";
            String[] whereArg = new String[] {String.valueOf(p.getID())};
            _db.delete(Inventory.InventoryEntry.TABLE_NAME, whereClause, whereArg);
            // notify the item removed by position
            // to perform recycler view delete animations
            notifyItemRemoved(position);
            Utils.updateView(_view, _filteredPurchases);
        }
    }

}
