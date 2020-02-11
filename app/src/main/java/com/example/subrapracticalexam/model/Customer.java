package com.example.subrapracticalexam.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Customer implements Serializable {

    @SerializedName("Customer_Id")
    @Expose
    private String customerId;
    @SerializedName("CustomerName")
    @Expose
    private String customerName;
    @SerializedName("ContactPerson")
    @Expose
    private String contactPerson;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("ContactNo")
    @Expose
    private String contactNo;
    @SerializedName("OCode")
    @Expose
    private String oCode;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getoCode() {
        return oCode;
    }

    public void setoCode(String oCode) {
        this.oCode = oCode;
    }
}
