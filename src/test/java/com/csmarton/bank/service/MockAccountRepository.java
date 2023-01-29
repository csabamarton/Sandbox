package com.csmarton.bank.service;

import com.csmarton.bank.model.Account;
import com.csmarton.bank.repo.AccountRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockAccountRepository implements AccountRepository {

    private Map<String, Account> accountMap = new HashMap<>();
    @Override
    public void saveAccount(Account account) throws Exception {
        accountMap.put(account.getIban(), account);
    }

    @Override
    public List<Account> getAccountsForTransaction(String senderIban, String recieverIban) throws Exception {
        return List.of(accountMap.get(senderIban), accountMap.get(recieverIban));
    }
}
