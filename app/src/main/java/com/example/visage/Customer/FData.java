package com.example.visage.Customer;

import java.util.ArrayList;

public class FData {

    ArrayList<String> available_merchants;

    public FData() {
    }

    public FData(ArrayList<String> available_merchants) {
        this.available_merchants = available_merchants;
    }

    public void setAvailable_merchants(ArrayList<String> available_merchants) {
        this.available_merchants = available_merchants;
    }

    public ArrayList<String> getAvailable_merchants() {
        return available_merchants;
    }
}
