package com.csmarton.bank.model;

import com.csmarton.bank.model.Account;

public class Transaction {
    private final String senderIban;

    private final String recieverIban;

    private final double amount;


    private TransactionStatus status;

    public Transaction(String senderAccount, String recieverAccount, double amount) {
        this.senderIban = senderAccount;
        this.recieverIban = recieverAccount;
        this.amount = amount;
        this.status = TransactionStatus.INIT;
    }

    public Transaction(String senderAccount, String recieverAccount, double amount, TransactionStatus status) {
        this.senderIban = senderAccount;
        this.recieverIban = recieverAccount;
        this.amount = amount;
        this.status = status;
    }

    public String getSenderIban() {
        return senderIban;
    }

    public String getRecieverIban() {
        return recieverIban;
    }
    public double getAmount() {
        return amount;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
    public TransactionStatus getStatus() {
        return status;
    }
}
