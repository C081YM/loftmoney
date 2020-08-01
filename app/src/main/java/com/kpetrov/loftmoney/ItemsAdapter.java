package com.kpetrov.loftmoney;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.MoneyViewHolder> {

    List<Item> items = new ArrayList<>();

    public void setData(List<Item> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void addData(List<Item> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void clearItems () {
        items.clear();
        notifyDataSetChanged();
    }

    void sortArrayList() {                                                           //сортировка
        Collections.sort(items, new Comparator<Item>() {
            @Override
            public int compare(Item i1, Item i2) {
                return i2.getDate().compareTo(i1.getDate());                         //если return i2 compareTo i1, то новые вверху списка
            }                                                                        // если return i1 compareTo i2, то новые внизу списка
        });
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MoneyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MoneyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false));
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
        TextView dateView;

        public MoneyViewHolder(@NonNull View itemView) {
            super(itemView);

            nameView = itemView.findViewById(R.id.itemNameView);
            priceView = itemView.findViewById(R.id.itemPriceView);
            dateView = itemView.findViewById(R.id.itemDateView);

        }

        public void bind(Item item) {

            nameView.setText(item.getName());
            priceView.setText(item.getPrice());
            priceView.setTextColor(ContextCompat.getColor(priceView.getContext(), item.getColor()));
            dateView.setText(item.getDate());                                                             // ввел для отображения даты, чтобы проверить сортировку по дате
        }
    }
}