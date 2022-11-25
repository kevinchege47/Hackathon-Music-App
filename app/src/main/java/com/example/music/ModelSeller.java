package com.example.music;

public class ModelSeller {
    String uid,username,email,password,phone,timestamp,online,accounttype;

    public ModelSeller() {
    }

    public ModelSeller(String uid, String username, String email, String password, String phone, String timestamp, String online, String accounttype) {
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.timestamp = timestamp;
        this.online = online;
        this.accounttype = accounttype;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public String getAccounttype() {
        return accounttype;
    }

    public void setAccounttype(String accounttype) {
        this.accounttype = accounttype;
    }
}
