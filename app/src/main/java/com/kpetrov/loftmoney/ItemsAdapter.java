package com.kpetrov.loftmoney;

import android.content.Context;
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
    private final int colorChoice;

    public ItemsAdapter(int colorChoice) {
        this.colorChoice = colorChoice;
    }

    /*public void setData(Item item) {
       items.clear();
       items.add(item);
       notifyDataSetChanged();
    }*/

    public void addData(Item item) {
        items.add(item);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MoneyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(),R.layout.item,null);
        return new MoneyViewHolder(view,colorChoice);
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

        public MoneyViewHolder(@NonNull View itemView, final int colorChoice) {
            super(itemView);

            nameView = itemView.findViewById(R.id.itemNameView);
            priceView = itemView.findViewById(R.id.itemPriceView);

            final Context context = priceView.getContext();
            priceView.setTextColor(ContextCompat.getColor(context,colorChoice));
        }

        public void bindItem(final Item item) {

            nameView.setText(item.getName());
            priceView.setText(priceView.getContext().getResources().getString(R.string.currency,String.valueOf(item.getPrice())));

        }
    }
}