package com.example.petvet;

public class LocationRequest {
    private String doctorLat;
    private String doctorLong;
    private String doctorUid;
    private String clientLat;
    private String clientLong;
    private String clientUid;
    public LocationRequest(){
    }
    public LocationRequest(String doctorLong,String doctorLat, String doctorUid,String clientLat,String clientLong,String clientUid){
        this.doctorLat = doctorLat;
        this.doctorLong = doctorLong;
        this.doctorUid = doctorUid;
        this.clientLat = clientLat;
        this.clientLong = clientLong;
        this.clientUid = clientUid;
    }
    public String getDoctorLong(){
        return doctorLong;
    }
    public String getDoctorLat(){
        return doctorLat;
    }
    public String getDoctorUid() {
        return doctorUid;
    }
    public String getClientLat() {
        return clientLat;
    }
    public String getClientLong() {
        return clientLong;
    }
    public String getClientUid() {
        return clientUid;
    }

    public void setDoctorLong(String doctorLong){
        this.doctorLong = doctorLong;
    }
    public void setClientLat(String clientLat) {
        this.clientLat = clientLat;
    }

    public void setClientLong(String clientLong) {
        this.clientLong = clientLong;
    }

    public void setClientUid(String clientUid) {
        this.clientUid = clientUid;
    }

    public void setDoctorLat(String doctorLat){
        this.doctorLat = doctorLat;
    }
    public void setDoctorUid(String doctorUid){
        this.doctorUid = doctorUid;
    }
}
