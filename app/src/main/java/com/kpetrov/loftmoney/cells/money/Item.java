package com.kpetrov.loftmoney.cells.money;

public class Item {
    private String name;
    private String value;
    private Integer color;

    public Item(String name, String value, Integer color) {
        this.name = name;
        this.value = value;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public Integer getColor() {
        return color;
    }

}
