package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class TransferDto {

        int transferId;
        String fromUserName;
        int transferStatusId;
        int transferToId;
        int transferTypeId;
        BigDecimal amount;
        int transferFromId;

        public int getTransferId() {
            return transferId;
        }

        public void setTransferId(int transferId) {
            this.transferId = transferId;
        }

        public String getFromUserName() {
            return fromUserName;
        }

        public int getTransferFromId() {
            return transferFromId;
        }

        public void setFromUserName(String fromUserName) {
            this.fromUserName = fromUserName;
        }

        public void setTransferFromId(int transferFromId) {
            this.transferFromId = transferFromId;
        }

        public int getTransferStatusId() {
            return transferStatusId;
        }

        public void setTransferStatusId(int transferStatusId) {
            this.transferStatusId = transferStatusId;
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
        public String getUsername() {
            return fromUserName;
        }
        public void setUserName(String userName) {
            this.fromUserName =  userName;
        }


    }
