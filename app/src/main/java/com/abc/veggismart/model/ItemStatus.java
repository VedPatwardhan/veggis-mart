package com.abc.veggismart.model;

public class ItemStatus {
    String name;
    double quantity;
    double price;
    String nameMarathi;
    String quantityUnit;
    int checked;

    public ItemStatus(){}

    public ItemStatus(String name, double quantity, double price, String nameMarathi, String quantityUnit, int checked) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.nameMarathi = nameMarathi;
        this.quantityUnit = quantityUnit;
        this.checked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getNameMarathi() {
        return nameMarathi;
    }

    public void setNameMarathi(String nameMarathi) {
        this.nameMarathi = nameMarathi;
    }

    public String getQuantityUnit() {
        return quantityUnit;
    }

    public void setQuantityUnit(String quantityUnit) {
        this.quantityUnit = quantityUnit;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }
}
