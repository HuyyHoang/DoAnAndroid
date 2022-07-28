package com.example.doancuoiky.budget;

public class GetCategoryRequest {
    private String username;
    private String accountName;

    public GetCategoryRequest(String username, String accountName) {
        this.username = username;
        this.accountName = accountName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
