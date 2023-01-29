package com.csmarton.bank.repo;

import com.csmarton.bank.model.Account;

import java.util.List;

public interface AccountRepository {

    void saveAccount(Account account) throws Exception;
    List<Account> getAccountsForTransaction(String senderIban, String recieverIban) throws Exception;

}
