package com.example.doancuoiky.account;

public class ItemAccountResponse {
    private String name;
    private Integer amount;
    private String message;

    public ItemAccountResponse() {}

    public ItemAccountResponse(String Name, Integer Amount) {
        name = Name;
        amount = Amount;
    }

    public ItemAccountResponse(String name) {
        this.name = name;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return name;
    }
}
