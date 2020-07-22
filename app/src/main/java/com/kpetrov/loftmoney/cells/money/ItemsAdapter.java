package com.kpetrov.loftmoney.cells.money;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.kpetrov.loftmoney.R;

import java.util.ArrayList;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.MoneyViewHolder> {

    private List<Item> items = new ArrayList<>();
    private ItemsAdapterClick itemsAdapterClick;

    public void setItemsAdapterClick(ItemsAdapterClick itemsAdapterClick) {
        this.itemsAdapterClick = itemsAdapterClick;
    }

    public void setData(List<Item> moneyCellModels) {
        this.items.clear();
        this.items.addAll(moneyCellModels);
        notifyDataSetChanged();
    }

    public void addData(List<Item> moneyCellModels) {
        this.items.addAll(moneyCellModels);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MoneyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MoneyViewHolder(itemsAdapterClick, LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MoneyViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class MoneyViewHolder extends RecyclerView.ViewHolder {
        TextView nameView;
        TextView priceView;
        ItemsAdapterClick itemsAdapterClick;

        public MoneyViewHolder(ItemsAdapterClick itemsAdapterClick, @NonNull View itemView) {
            super(itemView);
            this.itemsAdapterClick = itemsAdapterClick;

            nameView = itemView.findViewById(R.id.itemNameView);
            priceView = itemView.findViewById(R.id.itemPriceView);
        }

        public void bind(final Item item) {
            nameView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemsAdapterClick != null) {
                        itemsAdapterClick.onMoneyClick(item);
                    }
                }
            });

            priceView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemsAdapterClick != null) {
                        itemsAdapterClick.onValueClick(item.getPrice());
                    }
                }
            });

            nameView.setText(item.getName());
            priceView.setText(item.getPrice());
            priceView.setTextColor(ContextCompat.getColor(priceView.getContext(), item.getColor()));
        }
    }
}