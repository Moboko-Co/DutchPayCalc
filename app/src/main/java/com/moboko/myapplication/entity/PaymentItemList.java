package com.moboko.myapplication.entity;

public class PaymentItemList {

    int fromId;
    int toId;
    int typeDiv;
    int price;

    public PaymentItemList(int fromId, int toId, int price, int typeDiv) {
        this.fromId = fromId;
        this.toId = toId;
        this.price = price;
        this.typeDiv = typeDiv;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTypeDiv() {
        return typeDiv;
    }

    public void setTypeDiv(int typeDiv) {
        this.typeDiv = typeDiv;
    }

}
