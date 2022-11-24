package com.example.petvet;
public class VetAccount {
    private String paymentId;
    private String paymentAmount;
    private String paymentTime;
    private String vetId;
    private String serviceId;
    private String clientName;
    public VetAccount(){
    }
    public VetAccount(String paymentId, String paymentAmount,
                      String paymentTime,String vetId,String serviceId,String clientName){
        this.paymentId = paymentId;
        this.paymentAmount = paymentAmount;
        this.paymentTime = paymentTime;
        this.vetId = vetId;
        this.serviceId = serviceId;
        this.clientName = clientName;
    }
    public String getPaymentAmount() {
        return paymentAmount;
    }
    public String getPaymentTime() {
        return paymentTime;
    }

    public String getServiceId() {
        return serviceId;
    }

    public String getClientName() {
        return clientName;
    }

    public String getVetId() {
        return vetId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public void setVetId(String vetId) {
        this.vetId = vetId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setPaymentAmount(String paymentAmount){
        this.paymentAmount = paymentAmount;
    }
    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }
}
