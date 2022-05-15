package com.example.visage.Merchant;

public class MerchantsInfo {

    private String OwnerName;
    private String businessName;
    private String contactNumber;
    private String merchantAddress;
    private String businessType;
    private String working_hours;
    private String message;
    private String providing_home_service;

    public MerchantsInfo(){

    }

    public MerchantsInfo(String ownerName, String businessName, String contactNumber, String merchantAddress,
                         String businessType, String working_hours, String message, String providing_home_service) {
        OwnerName = ownerName;
        this.businessName = businessName;
        this.contactNumber = contactNumber;
        this.merchantAddress = merchantAddress;
        this.businessType = businessType;
        this.working_hours = working_hours;
        this.message = message;
        this.providing_home_service = providing_home_service;
    }

    public String getOwnerName() {
        return OwnerName;
    }

    public void setOwnerName(String ownerName) {
        OwnerName = ownerName;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getMerchantAddress() {
        return merchantAddress;
    }

    public void setMerchantAddress(String merchantAddress) {
        this.merchantAddress = merchantAddress;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getWorking_hours() {
        return working_hours;
    }

    public void setWorking_hours(String working_hours) {
        this.working_hours = working_hours;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getProviding_home_service() {
        return providing_home_service;
    }

    public void setProviding_home_service(String providing_home_service) {
        this.providing_home_service = providing_home_service;
    }
}
