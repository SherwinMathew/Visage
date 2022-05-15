package com.example.visage.Customer;

public class Booking {

    String location,contact_number,service_name,convenient_time;

    public Booking(String location, String contact_number, String service_name, String convenient_time) {
        this.location = location;
        this.contact_number = contact_number;
        this.service_name = service_name;
        this.convenient_time = convenient_time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getConvenient_time() {
        return convenient_time;
    }

    public void setConvenient_time(String convenient_time) {
        this.convenient_time = convenient_time;
    }
}
