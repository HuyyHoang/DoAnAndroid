package com.example.doancuoiky.account;

public class CreateRequest {
    private String username;
    private String name;
    private Integer amount;

    public  CreateRequest(String user, String bank, Integer money) {
        username = user;
        name = bank;
        amount = money;
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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
