package com.example.petvet;

public class ClientConfirmRequest {
    public String clientName;
    private String requestId;
    private String clientUserid;
    private String animCat;
    private String actualService;
    private String requestedTime;

    public ClientConfirmRequest() {
    }

    public ClientConfirmRequest(String clientName, String requestId, String clientUserid, String animCat, String actualService, String requestedTime) {
        this.clientName = clientName;
        this.requestId = requestId;
        this.clientUserid = clientUserid;
        this.animCat = animCat;
        this.actualService = actualService;
        this.requestedTime = requestedTime;
    }

    public String getClientName() {
        return clientName;
    }

    public String getRequestedTime() {
        return requestedTime;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getClientUserid() {
        return clientUserid;
    }

    public String getAnimCat() {
        return animCat;
    }

    public String getActualService() {
        return actualService;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public void setRequestedTime(String requestedTime) {
        this.requestedTime = requestedTime;
    }

    public void setClientUserid(String clientUserid) {
        this.clientUserid = clientUserid;
    }

    public void setAnimCat(String animCat) {
        this.animCat = animCat;
    }

    public void setActualService(String actualService) {
        this.actualService = actualService;
    }
}

