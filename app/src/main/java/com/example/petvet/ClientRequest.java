package com.example.petvet;

public class ClientRequest {
    private String requestID;
    private String serviceName;
    private String requestDescription;
    private String sex;
    private String category;
    private String clientID;
    private String imageUri;
    private String requestTime;
    private String clientName;
    private String vetID;

    public ClientRequest() {
    }

    public ClientRequest(String requestID, String serviceName,
                         String requestDescription,
                         String sex, String category,
                         String clientID, String imageUri,
                         String requestTime,String clientName,String vetID) {
        this.requestID = requestID;
        this.serviceName = serviceName;
        this.requestDescription = requestDescription;
        this.sex = sex;
        this.category = category;
        this.clientID = clientID;
        this.imageUri = imageUri;
        this.requestTime = requestTime;
        this.clientName = clientName;
        this.vetID = vetID;
    }

    //getters


    public String getClientName() {
        return clientName;
    }

    public String getVetID() {
        return vetID;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public String getImageUri() {
        return imageUri;
    }

    public String getRequestID() {
        return requestID;
    }

    public String getClientID() {
        return clientID;
    }


    public String getRequestDescription() {
        return requestDescription;
    }

    public String getSex() {
        return sex;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getCategory() {
        return category;
    }

    //setters
    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setVetID(String vetID) {
        this.vetID = vetID;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setRequestDescription(String requestDescription) {
        this.requestDescription = requestDescription;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }


    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}

