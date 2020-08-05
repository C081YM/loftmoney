package com.kpetrov.loftmoney;
import android.util.SparseBooleanArray;
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

    private ItemsAdapterListener mListener;
    private SparseBooleanArray mSelectedItems = new SparseBooleanArray();

    public void clearSelections() {
        mSelectedItems.clear();
        notifyDataSetChanged();
    }

    public void toggleItem (final int position) {
        mSelectedItems.put(position, !mSelectedItems.get(position));
        notifyDataSetChanged();
    }

    public void clearItem (final int position) {
        mSelectedItems.put(position,false);
        notifyDataSetChanged();
    }

    public int getSelectedSize() {
        int result = 0;
        for (int i = 0; i < items.size(); i++) {
            if (mSelectedItems.get(i)) {
                result++;
            }
        }
        return result;
    }

    public List<Integer> getSelectedItemsId () {
        List<Integer> result = new ArrayList<>();
        int i = 0;
        for (Item item: items) {
            if (mSelectedItems.get(i)) {
                result.add(Integer.valueOf(item.getId()));
            }
            i++;
        }
        return result;
    }

    public void setListener(final ItemsAdapterListener listener) {
        mListener = listener;
    }

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
        holder.bind(items.get(position),mSelectedItems.get(position));
        holder.setListener(mListener, items.get(position), position);
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

        public void bind(Item item, final boolean isSelected) {
            itemView.setSelected(isSelected);
            nameView.setText(item.getName());
            priceView.setText(item.getPrice());
            priceView.setTextColor(ContextCompat.getColor(priceView.getContext(), item.getColor()));
            dateView.setText(item.getDate());                                                             // ввел для отображения даты, чтобы проверить сортировку по дате
        }

        public void setListener(final ItemsAdapterListener listener, final Item item, final int position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item, position);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onItemLongClick(item, position);
                    return false;
                }
            });
        }
    }
}