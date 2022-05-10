package com.example.osnovy.models;

import com.google.gson.annotations.SerializedName;

public class RequestBody {

    @SerializedName("email")
    private String email;
    @SerializedName("username")
    private String username;
    @SerializedName("id")
    private int id;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
