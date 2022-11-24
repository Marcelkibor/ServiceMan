package com.example.petvet;
public class Payment {
    private String paymentId;
    private String requestUid;
    private String paymentAmount;
    private String paymentTime;
    private String clientID;
    public Payment(){
    }
    public Payment(String paymentId,String requestUid, String paymentAmount, String paymentTime,String clientID){
        this.paymentId = paymentId;
        this.requestUid = requestUid;
        this.paymentAmount = paymentAmount;
        this.paymentTime = paymentTime;
        this.clientID  = clientID;

    }

    public String getRequestUid(){
        return requestUid;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public String getClientID() {
        return clientID;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }
    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public void setRequestUid(String requestUid){
        this.requestUid = requestUid;
    }
    public void setPaymentAmount(String paymentAmount){
        this.paymentAmount = paymentAmount;
    }
    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }
}
