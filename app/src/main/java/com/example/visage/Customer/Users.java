package com.example.visage.Customer;

public class Users {

    public String name,email,mobilenumber;
    public Users(){

    }

    public Users(String name,String email,String mobilenumber){
        this.name = name;
        this.email = email;
        this.mobilenumber = mobilenumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }
}
