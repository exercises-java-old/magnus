package com.so4it.service;

import com.so4it.dao.AccountDao;
import com.so4it.dao.AccountDaoImpl;
import com.so4it.domain.Account;
import com.so4it.messaging.AccountProducer;

import java.util.concurrent.LinkedBlockingDeque;

public class ServiceFactoryImplOne implements ServiceFactory{


    @Override
    public AccountService createAccountService() {
        AccountDao accountDao = new AccountDaoImpl();
        accountDao.create(Account.builder().withId(1L).withBalance(100d).build());
        return new AccountServiceImpl(accountDao, new AccountProducer(new LinkedBlockingDeque<>()));
    }
}
