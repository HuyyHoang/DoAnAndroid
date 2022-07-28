package com.example.doancuoiky.transfer;

public class TransferRequest {
    private String username;
    private String fromBank;
    private String toBank;
    private Integer money;
    private String description;

    public TransferRequest() {}

    public TransferRequest(String username, String fromBank, String toBank, Integer money, String description) {
        this.username = username;
        this.fromBank = fromBank;
        this.toBank = toBank;
        this.money = money;
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFromBank() {
        return fromBank;
    }

    public void setFromBank(String fromBank) {
        this.fromBank = fromBank;
    }

    public String getToBank() {
        return toBank;
    }

    public void setToBank(String toBank) {
        this.toBank = toBank;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
