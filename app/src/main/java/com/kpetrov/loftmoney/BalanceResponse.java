package com.kpetrov.loftmoney;

import com.google.gson.annotations.SerializedName;

public class BalanceResponse {
    @SerializedName("status") String status;
    @SerializedName("total_expenses") String expenses;
    @SerializedName("total_income") String incomes;

    public String getStatus() {
        return status;
    }

    public String getExpenses() {
        return expenses;
    }

    public String getIncomes() {
        return incomes;
    }
}
