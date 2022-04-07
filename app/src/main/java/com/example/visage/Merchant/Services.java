package com.example.visage.Merchant;

public class Services {

    public String name,price,description,servicetype;

    public Services() {
    }

    public Services(String name, String price, String description, String servicetype) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.servicetype = servicetype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getServicetype() {
        return servicetype;
    }

    public void setServicetype(String servicetype) {
        this.servicetype = servicetype;
    }
}

