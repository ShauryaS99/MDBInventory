package com.jackie.mdbinventory;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.PurchaseViewHolder> {
    private Context _context;
    private ArrayList<Purchase> _purchases;

    public PurchaseAdapter(Context context, ArrayList<Purchase> purchases) {
        _context = context;
        _purchases = purchases;
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


    class PurchaseViewHolder extends RecyclerView.ViewHolder {
        private TextView _merchant;
        private TextView _description;
        private TextView _date;
        private TextView _cost;

        public PurchaseViewHolder(@NonNull View itemView) {
            super(itemView);
            _merchant = itemView.findViewById(R.id.merchantTextView);
            _description = itemView.findViewById(R.id.descTextView);
            _date = itemView.findViewById(R.id.dateTextView);
            _cost = itemView.findViewById(R.id.costTextView);
        }

        public void bind(int position) {
            Purchase p = _purchases.get(position);
            _merchant.setText(p.getMerchant());
            _description.setText(p.getDescription());
            _date.setText(p.getDate());
            _cost.setText(p.getCost());

        }
    }
}
