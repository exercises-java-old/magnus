package com.so4it.service;

import com.so4it.domain.Account;

public class AnotherAccountServiceImpl implements  AccountService{

    @Override
    public Double getBalance(Long id) {
        return 42d;
    }

    @Override
    public void create(Account account) {

    }
}
