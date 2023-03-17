package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BigDecimal getBalance(int userId) {
        String sql = "SELECT balance FROM account WHERE user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        BigDecimal balance = null;

        if(results.next()) {
            balance = results.getBigDecimal("balance");
        }
        return balance;
    }


    @Override
    public Account getAccountByAccountId(int accountId) {
        Account account = new Account();
        String sql = "SELECT account_id, user_id, balance FROM account WHERE account_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);

        while (results.next()){
           account = mapRowToAccount(results);
        }
            return account;
    }


    @Override
    public void updateAccountBalance(Account account, int account_id){
        String sql = "UPDATE account SET balance = ? WHERE account_id = ?;";
        jdbcTemplate.update(sql, account.getBalance(), account.getAccountId());

    }


    @Override
    public Account getAccountByUsername(String username) {
        Account account = new Account();
        String sql = "SELECT user_id, balance, account_id, username FROM account JOIN tenmo_user USING (user_id) WHERE username = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
        while (results.next()) {
             account =  mapRowToAccount(results);
        }
        return account;
    }


    @Override
    public int getAccountIdByUserId(int userId) {
        String sql = "SELECT account_id FROM account WHERE user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);

        return results.getInt("account_id");
    }

    @Override
    public BigDecimal updateBalanceSql(BigDecimal newBalance, int id) {
        String sql = "UPDATE account SET balance = ? WHERE user_id = ?;";
        jdbcTemplate.update(sql, newBalance, id);

        return getAccountByAccountId(id).getBalance();
    }

    @Override
    public int userIdByAccountId(int accountId) {
        String sql = "SELECT user_id FROM account WHERE account_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);

        return results.getInt("account_id");
    }

    private Account mapRowToAccount(SqlRowSet result) {
        Account account = new Account();
        account.setAccountId(result.getInt("account_id"));
        account.setUserId(result.getInt("user_id"));
        account.setBalance(result.getBigDecimal("balance"));
        return account;
    }
}
