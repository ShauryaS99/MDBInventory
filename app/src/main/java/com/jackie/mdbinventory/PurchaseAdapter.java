package com.jackie.mdbinventory;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/** Adapter for RecyclerView displaying Purchases made.
 * @author: Jacqueline Zhang
 * @date: 03/03/2019 */

public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.PurchaseViewHolder> {
    private Context _context;
    private ArrayList<Purchase> _purchases;
    private InventoryDbHelper _dbHelper;
    private SQLiteDatabase _db;

    public PurchaseAdapter(Context context, ArrayList<Purchase> purchases) {
        _context = context;
        _purchases = purchases;

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
        return new PurchaseViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseAdapter.PurchaseViewHolder purchaseViewHolder, int i) {
        purchaseViewHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        return _purchases.size();
    }


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


        }

        public void bind(int position) {
            Purchase p = _purchases.get(position);
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
                    // backup of removed item for undo purpose
                    final Purchase deletedPurchase = _purchases.get(getAdapterPosition());
                    final int deletedIndex = getAdapterPosition();

                    // remove the item from recycler view
                    removeItem(getAdapterPosition());
            }
        }

        public void removeItem(int position) {
            Purchase p = _purchases.get(position);
            _purchases.remove(position);
            String whereClause = "_id=?";
            String[] whereArg = new String[] {String.valueOf(p.getID())};
            _db.delete(Inventory.InventoryEntry.TABLE_NAME, whereClause, whereArg);
            // notify the item removed by position
            // to perform recycler view delete animations
            notifyItemRemoved(position);
        }
    }

}
