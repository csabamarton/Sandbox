package com.csmarton.bank.service;

import com.csmarton.bank.model.Account;
import com.csmarton.bank.model.Transaction;
import com.csmarton.bank.model.TransactionStatus;
import com.csmarton.bank.repo.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionServiceTest {

    private AccountService accountService;
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        accountService = new AccountService(new MockAccountRepository());
        transactionService = new TransactionService(accountService);
    }

    @Test
    void testTransferWithNotEnoughBalance() throws Exception {
        Account accountHU = new Account("HU123456", 100);
        Account accountDE = new Account("DE678947", 200);

        accountService.saveAccount(accountHU);
        accountService.saveAccount(accountDE);

        Transaction transaction = new Transaction("HU123456", "DE678947", 150);

        Transaction transactionResult = transactionService.initTransfer(transaction);

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

        Transaction transactionResult = transactionService.initTransfer(transaction);

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

        Transaction transactionResult = transactionService.initTransfer(transaction);
        Transaction transactionResult2 = transactionService.initTransfer(transaction2);

        assertNotNull(transactionResult);
        assertEquals(50, accountHU.getBalance());
        assertEquals(250, accountDE.getBalance());
        assertEquals(TransactionStatus.DONE, transactionResult.getStatus());

        assertNotNull(transactionResult2);
        assertEquals(50, accountHU.getBalance());
        assertEquals(250, accountDE.getBalance());
        assertEquals(TransactionStatus.FAILED_NO_CREDIT, transactionResult2.getStatus());
    }

    //TODO should be placed to a builder class
    private void createTwoAccounts() throws Exception {
        Account accountHU = new Account("HU123456", 100);
        Account accountDE = new Account("DE678947", 200);

        accountService.saveAccount(accountHU);
        accountService.saveAccount(accountDE);
    }
}