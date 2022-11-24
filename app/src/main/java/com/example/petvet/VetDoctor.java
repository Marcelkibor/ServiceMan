package com.example.petvet;

public class VetDoctor {
    private String doctorName;
    private String doctorEmail;
    private String doctorUid;
    private String doctorPassword;
    private String docLastName;
    private String docPhone;

    public VetDoctor() {
    }

    public VetDoctor(String doctorName, String doctorEmail, String doctorUid, String doctorPassword, String docLastName, String docPhone) {
        this.doctorName = doctorName;
        this.doctorEmail = doctorEmail;
        this.doctorUid = doctorUid;
        this.doctorPassword = doctorPassword;
        this.docLastName = docLastName;
        this.docPhone = docPhone;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getDoctorEmail() {
        return doctorEmail;
    }

    public String getDoctorUid() {
        return doctorUid;
    }

    public String getDocLastName() {
        return docLastName;
    }

    public String getDocPhone() {
        return docPhone;
    }

    public String getDoctorPassword() {
        return doctorPassword;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public void setDoctorEmail(String doctorEmail) {
        this.doctorEmail = doctorEmail;
    }

    public void setDoctorUid(String doctorUid) {
        this.doctorUid = doctorUid;
    }

    public void setDocLastName(String docLastName) {
        this.docLastName = docLastName;
    }

    public void setDocPhone(String docPhone) {
        this.docPhone = docPhone;
    }

    public void setDoctorPassword(String doctorPassword) {
        this.doctorPassword = doctorPassword;
    }
}
