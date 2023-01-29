package com.csmarton.bank.service;

import com.csmarton.bank.model.Account;
import com.csmarton.bank.model.Transaction;
import com.csmarton.bank.model.TransactionStatus;

import java.util.List;
import java.util.concurrent.Callable;

public class TransferCallable implements Callable<Transaction> {

    AccountService accountService;
    Transaction transaction;

    public TransferCallable(AccountService accountService, Transaction transaction) {
        this.accountService = accountService;
        this.transaction = transaction;
    }

    @Override
    public Transaction call() throws Exception {
        List<Account> accountsForTransaction = accountService.getAccountsForTransaction(transaction);

        Account sender = accountsForTransaction.get(0);
        Account reciever = accountsForTransaction.get(1);


        boolean withdrawResult = sender.withdraw(transaction.getAmount());

        if (!withdrawResult) {
            transaction.setStatus(TransactionStatus.FAILED_NO_CREDIT);
            return transaction;
        }

        boolean depositResult = reciever.deposit(transaction.getAmount());

        if(depositResult) {
            transaction.setStatus(TransactionStatus.DONE);
        } else {
            transaction.setStatus(TransactionStatus.FAILD_NO_REASON);
        }

        return transaction;
    }
}
