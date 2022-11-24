package com.example.petvet;

public class DoctorLocation {
    private String doctorLongitude;
    private String doctorLatitude;
    private String doctorUid;
    public DoctorLocation(){
    }
    public DoctorLocation(String doctorLongitude, String doctorLatitude, String doctorUid){
        this.doctorLatitude = doctorLatitude;
        this.doctorLongitude = doctorLongitude;
        this.doctorUid = doctorUid;
    }
    public String getDoctorLongitude(){
        return doctorLongitude;
    }
    public String getDoctorLatitude(){
        return doctorLatitude;
    }
    public String getDoctorUid() {
        return doctorUid;
    }

    public void setDoctorLongitude(String doctorLongitude){
        this.doctorLongitude = doctorLongitude;
    }
    public void setDoctorLatitude(String doctorLatitude){
        this.doctorLatitude = doctorLatitude;
    }
    public void setDoctorUid(String doctorUid){
        this.doctorUid = doctorUid;
    }
}
