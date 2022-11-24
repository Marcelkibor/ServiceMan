package com.example.petvet;
public class BusinessAccount {
    private String paymentId;
    private String paymentAmount;
    private String paymentTime;
    private String docId;
    private String serviceId;
    public BusinessAccount(){
    }
    public BusinessAccount(String paymentId, String paymentAmount, String paymentTime,String docId,String serviceId){
        this.paymentId = paymentId;
        this.paymentAmount = paymentAmount;
        this.paymentTime = paymentTime;
        this.docId = docId;
        this.serviceId = serviceId;
    }
    public String getPaymentAmount() {
        return paymentAmount;
    }
    public String getPaymentTime() {
        return paymentTime;
    }

    public String getDocId() {
        return docId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public void setPaymentAmount(String paymentAmount){
        this.paymentAmount = paymentAmount;
    }
    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }
}
