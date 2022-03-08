package com.example.visage.Customer;

public class ServiceItem {

    private String name;
    private float rating;
    private int price;
    private int image;

    public ServiceItem(String name, float rating, int price, int image){
        this.name = name;
        this.rating = rating;
        this.price = price;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

//    public void add(ServiceItem serviceItem) {
//    }
}
