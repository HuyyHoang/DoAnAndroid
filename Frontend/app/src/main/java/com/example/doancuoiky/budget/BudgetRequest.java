package com.example.doancuoiky.budget;

public class BudgetRequest {
    private String username;
    private String name;
    private String accountName;
    private int month;

    public BudgetRequest(String username, String accountName, int month) {
        this.username = username;
        this.accountName = accountName;
        this.month = month;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
