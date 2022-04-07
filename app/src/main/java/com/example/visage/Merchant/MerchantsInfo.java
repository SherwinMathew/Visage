package com.example.visage.Merchant;

public class MerchantsInfo {

    private String OwnerName;
    private String businessName;
    private String contactNumber;
    private String merchantAddress;
    private String businessType;
    private String availServices;
    private String message;

    public MerchantsInfo(){

    }

    public MerchantsInfo(String ownerName, String businessName, String contactNumber, String merchantAddress, String businessType, String availServices, String message) {
        OwnerName = ownerName;
        this.businessName = businessName;
        this.contactNumber = contactNumber;
        this.merchantAddress = merchantAddress;
        this.businessType = businessType;
        this.availServices = availServices;
        this.message = message;
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
        this.merchantAddress = merchantAddress; }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getAvailServices() {
        return availServices;
    }

    public void setAvailServices(String availServices) {
        this.availServices = availServices;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
