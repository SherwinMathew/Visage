package com.example.visage.Customer;

public class Users {

    public String name,email,mobilenumber,user_type;
    public Users(){

    }

    public Users(String name,String email,String mobilenumber,String user_type){
        this.name = name;
        this.email = email;
        this.mobilenumber = mobilenumber;
        this.user_type = user_type;
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

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
}
