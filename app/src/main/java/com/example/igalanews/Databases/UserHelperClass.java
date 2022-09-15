package com.example.igalanews.Databases;

public class UserHelperClass {

    String fullName, username, location, email, password, phoneNo;


    public UserHelperClass (){}

    public UserHelperClass(String fullName, String username, String location, String email, String password, String phoneNo) {
        this.fullName = fullName;
        this.username = username;
        this.location = location;
        this.email = email;
        this.password = password;
        this.phoneNo = phoneNo;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
