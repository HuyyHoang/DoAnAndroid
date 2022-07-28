package com.example.doancuoiky.account;

public class GetIncomeExpenseRequest {
    private String username;
    private String name_bank;

    public GetIncomeExpenseRequest(String username, String name_bank) {
        this.username = username;
        this.name_bank = name_bank;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName_bank() {
        return name_bank;
    }

    public void setName_bank(String name_bank) {
        this.name_bank = name_bank;
    }
}
