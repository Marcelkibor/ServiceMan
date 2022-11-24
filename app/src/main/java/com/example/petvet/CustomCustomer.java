package com.example.petvet;

public class CustomCustomer {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String customerUid;

    public CustomCustomer() {
    }

    public CustomCustomer(String firstName, String lastName, String phone, String email, String customerUid) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.customerUid = customerUid;
    }

    public String getCustomerUid() {
        return customerUid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public void setCustomerUid(String customerUid) {
        this.customerUid = customerUid;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
