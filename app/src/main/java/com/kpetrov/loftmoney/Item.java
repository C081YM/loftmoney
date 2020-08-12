package com.kpetrov.loftmoney;

public class Item {
    private String name;
    private String price;
    private Integer color;
    private String date;
    private String id;

    public Item(String name, String price, Integer color, String date, String id) {
        this.name = name;
        this.price = price;
        this.color = color;
        this.date = date;
        this.id = id;
    }

    public static Item getInstance(MoneyItem moneyItem) {
        return new Item(moneyItem.getName(),moneyItem.getPrice() + " ₽", moneyItem.getType().equals("expense") ? R.color.colorItemPrice : R.color.colorItemIncome, moneyItem.getDate(),moneyItem.getId());
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getColor() {
    return color;
   }

    public String getDate() {
        return date;
    }

    public String getId() {
        return id;
    }
}
