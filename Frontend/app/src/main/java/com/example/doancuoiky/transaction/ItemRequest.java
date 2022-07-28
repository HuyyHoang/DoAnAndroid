package com.example.doancuoiky.transaction;

public class ItemRequest {
    private String username;
    private String accountName;
    private String filterBy;
    private String sortBy;

    public ItemRequest(String username, String accountName, String filterBy, String sortBy) {
        this.username = username;
        this.accountName = accountName;
        this.filterBy = filterBy;
        this.sortBy = sortBy;
    }

    public ItemRequest(String username, String accountName) {
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

    public String getFilterBy() {
        return filterBy;
    }

    public void setFilterBy(String filterBy) {
        this.filterBy = filterBy;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
}
