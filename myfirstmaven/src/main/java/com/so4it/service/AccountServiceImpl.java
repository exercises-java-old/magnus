package com.so4it.service;

import com.so4it.dao.AccountDao;
import com.so4it.dao.AccountDaoImpl;
import com.so4it.domain.Account;
import com.so4it.messaging.AccountProducer;

import java.util.Objects;
import java.util.Optional;

/**
 * Singleton
 *
 */
public class AccountServiceImpl implements  AccountService{

    private final AccountDao accountDao;

    private final AccountProducer accountProducer;

    public AccountServiceImpl(AccountDao accountDao,AccountProducer accountProducer) {
        this.accountDao = Objects.requireNonNull(accountDao,"accountDao cannot be null");
        this.accountProducer = Objects.requireNonNull(accountProducer,"accountProducer cannot be null");
    }

    @Override
    public Double getBalance(Long id) {
        Optional<Account> account = accountDao.read(id);
        if(account.isPresent()){
            return account.get().getBalance();
        }
        return 0.0d;
    }


    @Override
    public void create(Account account) {
        accountProducer.create(account);

    }
}
