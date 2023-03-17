package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {

    @Autowired
     AccountDao accountDao;

    //get specific account by account_id - this works!
    @RequestMapping(path = "/account/{id}", method = RequestMethod.GET)
    public Account getAccount(@PathVariable int id) {
        return accountDao.getAccountByAccountId(id);
    }

    //returns the current balance of the logged in user - this works!
    @RequestMapping(path = "/account/balance", method = RequestMethod.GET)
    public BigDecimal getBalance(Principal principal) {
        return accountDao.getAccountByUsername(principal.getName()).getBalance();
    }

    //returns the current balance of any account by account id - this works!
    @RequestMapping(path = "/account/{id}/balance", method = RequestMethod.GET)
    public BigDecimal getBalance(@PathVariable int id) {
        return accountDao.getBalance(id);
    }


    @RequestMapping(path = "/accounts/{id}",method = RequestMethod.PUT)
    public void updateAccountByID(@RequestBody Account account, @PathVariable int accountId) {
        accountDao.updateAccountBalance(account, accountId);
    }

    @RequestMapping(path = "/account/user/{id}", method = RequestMethod.GET)
    public int getAccountId(@PathVariable int id) {
        return accountDao.getAccountIdByUserId(id);
    }

    @RequestMapping(path = "/account/getuser", method = RequestMethod.GET)
    public int getUserIdByAccountId(@PathVariable int id) {
        return accountDao.userIdByAccountId(id);
    }



    }



