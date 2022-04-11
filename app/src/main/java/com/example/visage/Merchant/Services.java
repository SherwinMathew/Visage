package com.example.visage.Merchant;

public class Services {
    
    public String name,price,description,service_type;

    public Services() {
    }

    public Services(String name, String price, String description, String service_type) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.service_type = service_type;
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

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }
}

