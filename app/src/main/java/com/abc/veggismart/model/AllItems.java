package com.abc.veggismart.model;

public class AllItems {
    int id;
    String imageURL;
    String name;
    double quantity;
    double price;
    String nameMarathi;
    String quantityUnit;

    public AllItems() {
        this.id=0;
        this.imageURL="";
        this.name="";
        this.nameMarathi="";
        this.quantityUnit="";
        this.quantity=0.0;
        this.price=0.0;
    }

    public AllItems(int id, String imageURL, String name, double quantity, double price, String nameMarathi, String quantityUnit) {
        this.id = id;
        this.imageURL = imageURL;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.nameMarathi=nameMarathi;
        this.quantityUnit=quantityUnit;
    }

    public String getQuantityUnit() {
        return quantityUnit;
    }

    public void setQuantityUnit(String quantityUnit) {
        this.quantityUnit = quantityUnit;
    }

    public String getNameMarathi() {
        return nameMarathi;
    }

    public void setNameMarathi(String nameMarathi) {
        this.nameMarathi = nameMarathi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
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
}
