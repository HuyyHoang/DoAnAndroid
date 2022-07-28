package com.example.doancuoiky.transaction;

public class CreateIncomeRequest {
    private String username;
    private Integer amount;
    private String category_name;
    private String description;
    private String name;

    public CreateIncomeRequest(String username, Integer amount, String description, String bank_name , String category) {
        this.username = username;
        this.amount = amount;
        this.description = description;
        this.name = bank_name;
        this.category_name = category;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
