package com.example.osnovy.models;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {

    @SerializedName("password")
    private String password;
    @SerializedName("username")
    private String username;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}


