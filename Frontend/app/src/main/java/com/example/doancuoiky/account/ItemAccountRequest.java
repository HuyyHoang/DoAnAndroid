package com.example.doancuoiky.account;

public class ItemAccountRequest {
    private String username;

    public ItemAccountRequest(String name) {
        username = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
