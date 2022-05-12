package com.example.visage.Merchant;

public class Services {
    
    public String price,description;

    public Services() {
    }

    public Services(String price, String description) {
        this.price = price;
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

