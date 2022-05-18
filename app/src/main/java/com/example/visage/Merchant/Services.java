package com.example.visage.Merchant;

public class Services {
    
    public String price,description,service_name;

    public Services() {
    }

    public Services(String price, String description, String service_name) {
        this.price = price;
        this.description = description;
        this.service_name = service_name;
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

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }
}

