package com.example.doancuoiky.budget;

import java.io.Serializable;

public class BudgetResponse implements Serializable {
    private String name;
    private String amount_total;
    private String amount_spent;
    private String amount_remain;


    public String getBudgetName() {
        return name;
    }

    public void setBudgetName(String budgetName) {
        this.name = budgetName;
    }

    public String getAmount() {
        return amount_total;
    }

    public void setAmount(String amount) {
        amount_total = amount;
    }

    public String getAmount_Spent() {
        return amount_spent;
    }

    public void setAmount_Spent(String amount_Spent) {
        amount_spent = amount_Spent;
    }

    public String getAmount_Remain() {
        return amount_remain;
    }

    public void setAmount_Remain(String amount_Remain) {
        amount_remain = amount_Remain;
    }
}
