package com.so4it.messaging;

import com.so4it.dao.AccountDao;
import com.so4it.domain.Account;

import java.util.Objects;

public class AccountListenerImpl implements AccountListener{


    private AccountDao accountDao;

    public AccountListenerImpl(AccountDao accountDao) {
        this.accountDao = Objects.requireNonNull(accountDao,"accountDao");
    }

    @Override
    public void onAccount(Account account) {
        accountDao.create(account);
    }
}
