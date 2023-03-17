package com.techelevator.tenmo.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class TransferDto {

    @NotNull
    int transferToId;
    String transferToUsername;
    @Positive(message = "You cannot send a negative amount. That would probably cause a banking crisis.")
    BigDecimal amount;
    @NotNull
    int transferTypeId;
    @NotNull
    int transferStatusId;
    String transferStatusName;
    int transferFromId;
    int transferId;


    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public String getTransferToUsername() {
        return transferToUsername;
    }

    public int getTransferFromId() {
        return transferFromId;
    }

    public void setTransferToUsername(String transferToUsername) {
        this.transferToUsername = transferToUsername;
    }

    public void setTransferStatusName(String transferStatusName) {
        this.transferStatusName = transferStatusName;
    }

    public void setTransferFromId(int transferFromId) {
        this.transferFromId = transferFromId;
    }


    public int getTransferToId() {
        return transferToId;
    }

    public void setTransferToId(int transferToId) {
        this.transferToId = transferToId;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public int getTransferTypeId() {
        return transferTypeId;
    }
    public void setTransferTypeId(int transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public int getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(int transferStatusId) {
        this.transferStatusId = transferStatusId;
    }


}