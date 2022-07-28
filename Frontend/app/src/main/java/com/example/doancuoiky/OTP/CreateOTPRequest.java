package com.example.doancuoiky.OTP;

public class CreateOTPRequest {
    private String username;

    public CreateOTPRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
