package com.example.petvet;

public class AdminClass {
    private String adminName;
    private String adminEmail;
    private String adminUid;
    public AdminClass(){
    }
    public AdminClass(String adminName, String adminEmail, String adminUid){
        this.adminName = adminName;
        this.adminEmail = adminEmail;
        this.adminUid = adminUid;
    }
    public String getAdminName(){
        return adminName;
    }
    public String getAdminEmail(){
        return adminEmail;
    }
    public String getAdminUid() {
        return adminUid;
    }
    public void setAdminName(String adminName){this.adminName = adminName;}
    public void setAdminEmail(String adminEmail){
        this.adminEmail = adminEmail;
    }
    public void setAdminUid(String adminUid){
        this.adminUid = adminUid;
    }
}
