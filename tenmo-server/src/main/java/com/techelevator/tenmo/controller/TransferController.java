package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.InsufficientFundsException;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {

    @Autowired
    TransferDao transferDao;
    @Autowired
    AccountDao accountDao;
    @Autowired
    UserDao userDao;

    public TransferController (TransferDao transferDao, AccountDao accountDao, UserDao userDao){
        this.transferDao = transferDao;
        this.accountDao = accountDao;
        this.userDao = userDao;
    }

    //get specific transfer by id - this works!
    @RequestMapping(path = "/transfer/{id}/no", method = RequestMethod.GET)
    public Transfer transfersById(@PathVariable int id) {
        return transferDao.getTransfer(id);
    }

    //get list of all transfers - this works!
    @RequestMapping(path = "/transfer/list", method = RequestMethod.GET)
    public List<Transfer> getAllTransfers() {
        return transferDao.getAllTransfers();
    }

    @RequestMapping(path = "/transfers/username/{id}", method = RequestMethod.GET)
    public String getThatUsername(@PathVariable int userId){
        return userDao.findUsernameById(userId);
    }

    //get list of all transfers for a given account id - this works!
    @RequestMapping(path = "/transfers/{id}", method = RequestMethod.GET)
    public List<Transfer> listAllTransfersById(@PathVariable int id) {
        return transferDao.transfersByAccountId(id);
    }

    //get a list of all pending transfers for a given account id - this works!
    @RequestMapping(path= "/transfers/pending/{accountId}", method = RequestMethod.GET)
    public List<Transfer> listAllPendingTransfersById(@PathVariable int accountId) {
        return transferDao.getPendingTransfers(accountId);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/transfer/create", method = RequestMethod.POST)
    public Transfer transfer(@Valid @RequestBody TransferDto transferDto, Principal principal) {

        transferDto.setTransferFromId((accountDao.getAccountByUsername(principal.getName()).getUserId()) + 1000); //we need ACCOUNT_ID, not user_Id

        BigDecimal fromAccountBalance = accountDao.getBalance(accountDao.getAccountByUsername(principal.getName()).getUserId());
        BigDecimal newFromAccountBalance = fromAccountBalance.subtract(transferDto.getAmount());

            //user acct cannot be negative
        if (newFromAccountBalance.compareTo(BigDecimal.ZERO) >= 0) {
            //writes the transfer to the DB
            int id = transferDao.createTransfer(transferDto);

            //update balance in fromaccount to subtract amount
            accountDao.updateBalanceSql(newFromAccountBalance, transferDto.getTransferFromId() - 1000);

            //add amount to toaccount
            BigDecimal newToAccountBalance = accountDao.getAccountByAccountId(transferDto.getTransferToId()).getBalance().add(transferDto.getAmount());
            //update balance in toaccount add amount
            accountDao.updateBalanceSql(newToAccountBalance, transferDto.getTransferToId() - 1000);


            //returns transfer ID for confirmation
            return transferDao.getTransferByID(id).get(0);

        } else {
            System.out.println("Cannot complete transfer - Insufficient funds.");
            return null;
        }
    }

/**
    holy shit this works.
    {
            "transferId": 3002,
            "transferTypeId": 1,
            "transferStatusId": 1,
            "accountFromId": 2001,
            "accountToId": 2002,
            "amount": 100.00
    }
     the above body returned 'true' when using PUT in postman.
     upon checking the transfer_id (first method in this controller) the transfer_status_id
     has been updated from 1 to 2. This changes transfers from (1)PENDING to (2)APPROVED
 **/
     @RequestMapping(path = "/transfers/status/{transferId}/{statusId}", method = RequestMethod.PUT)
    public boolean updateTransfer(@RequestBody Transfer transfer) {
        transferDao.updateTransfer(transfer);
        return true;
    }

}
