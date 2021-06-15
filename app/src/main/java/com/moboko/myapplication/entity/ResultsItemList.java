package com.moboko.myapplication.entity;

import java.io.Serializable;

public class ResultsItemList implements Serializable {
    int id;
    int price;

    public ResultsItemList(int id, int price) {
        this.id = id;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
