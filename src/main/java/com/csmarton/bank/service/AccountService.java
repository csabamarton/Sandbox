package com.csmarton.bank.service;

import com.csmarton.bank.model.Account;
import com.csmarton.bank.model.Transaction;
import com.csmarton.bank.repo.AccountRepository;

import java.util.List;

public class AccountService {

    AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAccountsForTransaction(Transaction transaction) throws Exception {
        return accountRepository.getAccountsForTransaction(transaction.getSenderIban(), transaction.getRecieverIban());
    }

    public void saveAccount(Account account) throws Exception {
        accountRepository.saveAccount(account);
    }
}
