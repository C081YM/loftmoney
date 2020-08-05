package com.kpetrov.loftmoney;

import com.google.gson.annotations.SerializedName;

public class BalanceResponse {
    @SerializedName("status") private String status;
    @SerializedName("total_expenses") private String totalExpenses;
    @SerializedName("total_income") private String totalIncomes;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(String totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public String getTotalIncomes() {
        return totalIncomes;
    }

    public void setTotalIncomes(String totalIncomes) {
        this.totalIncomes = totalIncomes;
    }
}
