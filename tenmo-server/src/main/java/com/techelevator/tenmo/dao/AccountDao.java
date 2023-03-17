package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;

public interface AccountDao {

    BigDecimal getBalance(int userId);

//    Account getAccountByUserId(int userId);

    Account getAccountByAccountId(int accountId);

//    String getUsernameByAccountId(int accountId);

    Account getAccountByUsername(String username);

//    int getAccountIdByUsername(String username);

    int getAccountIdByUserId(int userId);

    void updateAccountBalance(Account account, int accountId);

    BigDecimal updateBalanceSql(BigDecimal newBalance, int id);

//    Account findAccountById(int id);

    int userIdByAccountId(int accountId);
}
