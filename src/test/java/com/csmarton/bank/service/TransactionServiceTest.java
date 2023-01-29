package com.csmarton.bank.service;

import com.csmarton.bank.model.Account;
import com.csmarton.bank.model.Transaction;
import com.csmarton.bank.model.TransactionStatus;
import com.csmarton.bank.repo.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.*;

class TransactionServiceTest {

    private AccountService accountService;
    private TransactionService transactionService;

    private ExecutorService executorService;

    @BeforeEach
    void setUp() {
        accountService = new AccountService(new MockAccountRepository());
        executorService = Executors.newFixedThreadPool(5);
        transactionService = new TransactionService(executorService, accountService);
    }

    @Test
    void testTransferWithNotEnoughBalance() throws Exception {
        Account accountHU = new Account("HU123456", 100);
        Account accountDE = new Account("DE678947", 200);

        accountService.saveAccount(accountHU);
        accountService.saveAccount(accountDE);

        Transaction transaction = new Transaction("HU123456", "DE678947", 150);

        Transaction transactionResult = transactionService.initTransfer(transaction).get();

        assertNotNull(transactionResult);
        assertEquals(100, accountHU.getBalance());
        assertEquals(200, accountDE.getBalance());
        assertEquals(TransactionStatus.FAILED_NO_CREDIT, transactionResult.getStatus());
    }

    @Test
    void testTransferWithEnoughBalance() throws Exception {
        createTwoAccounts();

        Transaction transaction = new Transaction("HU123456", "DE678947", 50);
        List<Account> accountsForTransaction = accountService.getAccountsForTransaction(transaction);

        Transaction transactionResult = transactionService.initTransfer(transaction).get();

        assertNotNull(transactionResult);
        assertEquals(50, accountsForTransaction.get(0).getBalance());
        assertEquals(250, accountsForTransaction.get(1).getBalance());
        assertEquals(TransactionStatus.DONE, transactionResult.getStatus());
    }

    @Test
    void testTwoTransferWithNotEnoughBalanceForTwo() throws Exception {
        Account accountHU = new Account("HU123456", 100);
        Account accountDE = new Account("DE678947", 200);

        accountService.saveAccount(accountHU);
        accountService.saveAccount(accountDE);

        Transaction transaction = new Transaction("HU123456", "DE678947", 50);
        Transaction transaction2 = new Transaction("HU123456", "DE678947", 100);

        Future<Transaction> transactionResult = transactionService.initTransfer(transaction);
        Future<Transaction> transactionResult2 = transactionService.initTransfer(transaction2);

        Transaction transaction1Result = transactionResult.get();
        Transaction transaction2Result = transactionResult2.get();

        assertNotNull(transaction1Result);
        assertEquals(50, accountHU.getBalance());
        assertEquals(250, accountDE.getBalance());
        assertEquals(TransactionStatus.DONE, transaction1Result.getStatus());

        assertNotNull(transaction2Result);
        assertEquals(50, accountHU.getBalance());
        assertEquals(250, accountDE.getBalance());
        assertEquals(TransactionStatus.FAILED_NO_CREDIT, transaction2Result.getStatus());
    }

    //TODO should be placed to a builder class
    private void createTwoAccounts() throws Exception {
        Account accountHU = new Account("HU123456", 100);
        Account accountDE = new Account("DE678947", 200);

        accountService.saveAccount(accountHU);
        accountService.saveAccount(accountDE);
    }
}