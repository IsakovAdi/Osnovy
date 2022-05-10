package com.example.osnovy.models;

import com.google.gson.annotations.SerializedName;

public class TokenResponse {

    @SerializedName("auth_token")
    private String auth_token;


    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }
}
