package com.techelevator.tenmo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class Account {
    @JsonProperty("user_id")
    private int userId;
    @JsonProperty("account_id")
    private int accountId;
    @JsonProperty("balance")
    BigDecimal balance;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }


    @Override
    public String toString() {
        return "Account{" +
                "userId=" + userId +
                ", accountId=" + accountId +
                ", balance=" + balance +
                '}';
    }
}
