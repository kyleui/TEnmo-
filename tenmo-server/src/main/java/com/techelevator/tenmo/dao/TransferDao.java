package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDto;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    List<Transfer> getAllTransfers();
    List<Transfer> transfersByAccountId(int accountId);


    Transfer getTransfer(int transferId);

    int createTransfer(TransferDto transferDto);

    boolean updateTransfer(Transfer transfer);

    List<Transfer> getPendingTransfers(int accountId);

    List<Transfer> getTransferByID(int transferId);
}