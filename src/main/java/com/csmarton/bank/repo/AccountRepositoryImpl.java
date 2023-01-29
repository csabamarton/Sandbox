package com.csmarton.bank.repo;

import com.csmarton.bank.model.Account;

import java.util.List;

public class AccountRepositoryImpl implements AccountRepository {

    @Override
    public void saveAccount(Account account) throws Exception {
        throw new Exception("Not Implemented yet - saveAccount");
    }

    @Override
    public List<Account> getAccountsForTransaction(String senderIban, String recieverIban) throws Exception {
        throw new Exception("Not Implemented yet - getAccountsForTransaction");
    }
}
