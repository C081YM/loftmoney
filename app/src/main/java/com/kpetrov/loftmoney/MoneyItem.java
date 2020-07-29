package com.kpetrov.loftmoney;

import com.google.gson.annotations.SerializedName;

public class MoneyItem {
    private String id;
    private String name;
    private int price;
    private String type;
    private String date;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }
}
