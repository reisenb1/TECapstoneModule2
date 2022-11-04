package com.techelevator.tenmo.model;

import com.techelevator.tenmo.services.TenmoService;

import java.math.BigDecimal;

public class Transfer {

    private int transferId;
    private int transferTypeId;
    private int transferStatusId;
    private int accountIdFrom;
    private int accountIdTo;
    private BigDecimal amount;
    private String username;

    public Transfer() {
    }

    public Transfer(int transferId, int transferTypeId, int transferStatusId, int accountIdFrom, int accountIdTo, BigDecimal amount, String username) {
        this.transferId = transferId;
        this.transferTypeId = transferTypeId;
        this.transferStatusId = transferStatusId;
        this.accountIdFrom = accountIdFrom;
        this.accountIdTo = accountIdTo;
        this.amount = amount;
        this.username = username;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
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

    public int getAccountIdFrom() {
        return accountIdFrom;
    }

    public void setAccountIdFrom(int accountIdFrom) {
        this.accountIdFrom = accountIdFrom;
    }

    public int getAccountIdTo() {
        return accountIdTo;
    }

    public void setAccountIdTo(int accountIdTo) {
        this.accountIdTo = accountIdTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString () {
        String typeDescription = new String();

        if (transferTypeId == 2) {
            typeDescription = "To";
        } else if (transferTypeId == 1) {
            typeDescription = "From";
        }
        String output = String.format("%d %4s: %s  $%.2f%n", transferId, typeDescription, username, amount);
        return output;
    }
}
