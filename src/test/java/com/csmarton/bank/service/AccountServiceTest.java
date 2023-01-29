package com.csmarton.bank.service;

import com.csmarton.bank.model.Account;
import com.csmarton.bank.model.Transaction;
import com.csmarton.bank.repo.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    private AccountRepository accountRepository = new MockAccountRepository();
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        accountRepository = new MockAccountRepository();
        accountService = new AccountService(accountRepository);
    }

    @Test
    void testSavingAndFetchingAccount() throws Exception {
        Account accountHU = new Account("HU123456", 100);
        Account accountDE = new Account("DE678947", 200);

        accountService.saveAccount(accountHU);
        accountService.saveAccount(accountDE);

        Transaction transaction = new Transaction("HU123456", "DE678947", 50);

        List<Account> accountsForTransaction = accountService.getAccountsForTransaction(transaction);

        assertNotNull(accountsForTransaction);
        assertEquals(2, accountsForTransaction.size());
        assertEquals(accountHU, accountsForTransaction.get(0));
        assertEquals(accountDE, accountsForTransaction.get(1));
    }

}