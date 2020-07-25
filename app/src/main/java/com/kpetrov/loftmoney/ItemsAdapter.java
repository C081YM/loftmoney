package com.kpetrov.loftmoney;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.MoneyViewHolder> {

    private List<Item> items = new ArrayList<>();

    public void addData(Item item) {
        items.add(item);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MoneyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MoneyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MoneyViewHolder holder, int position) {
        holder.bindItem(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class MoneyViewHolder extends RecyclerView.ViewHolder {
        private TextView nameView;
        private TextView priceView;

        public MoneyViewHolder(@NonNull View itemView) {
            super(itemView);

            nameView = itemView.findViewById(R.id.itemNameView);
            priceView = itemView.findViewById(R.id.itemPriceView);
        }

        public void bindItem(final Item item) {

            nameView.setText(item.getName());
            priceView.setText(priceView.getContext().getResources().getString(R.string.currency,String.valueOf(item.getPrice())));
            priceView.setTextColor(ContextCompat.getColor(priceView.getContext(), item.getColor()));
        }
    }
}