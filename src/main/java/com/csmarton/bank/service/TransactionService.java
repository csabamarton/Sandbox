package com.csmarton.bank.service;

import com.csmarton.bank.model.Account;
import com.csmarton.bank.model.Transaction;
import com.csmarton.bank.model.TransactionStatus;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class TransactionService {

    ExecutorService executorService;

    AccountService accountService;

    public TransactionService(ExecutorService executorService, AccountService accountService) {
        this.executorService = executorService;
        this.accountService = accountService;
    }

    public Future<Transaction> initTransfer(Transaction transaction) throws Exception {

        Callable transferCallable = new TransferCallable(accountService, transaction);

        Future<Transaction> future = executorService.submit(transferCallable);

        return future;
    }
}
