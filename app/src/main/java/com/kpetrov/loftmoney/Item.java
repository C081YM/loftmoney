package com.kpetrov.loftmoney;

public class Item {
    private String name;
    private int price;
    //private Integer color;

    public Item(String name, int price) {
        this.name = name;
        this.price = price;
        //this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    //public Integer getColor() {
   //return color;
   //

}
