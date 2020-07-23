package com.kpetrov.loftmoney.cells.money;



public class Item {
    private String name;
    private String price;
    private Integer color;

    public Item(String name, String price, Integer color) {
        this.name = name;
        this.price = price;
        this.color = color;
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

}
