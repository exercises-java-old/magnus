package com.so4it.service;

import com.so4it.dao.AccountDao;
import com.so4it.dao.AccountDaoImpl;
import com.so4it.domain.Account;

import java.util.Collection;
import java.util.Optional;

public class ServiceFactoryImplTwo implements ServiceFactory{


    @Override
    public AccountService createAccountService() {
        return new AccountServiceImpl(new AccountDaoMock());
    }



    public static class AccountDaoMock implements AccountDao{
        @Override
        public void create(Account account) {

        }

        @Override
        public Optional<Account> read(Long id) {
            System.out.println("Im the second one");
            return Optional.empty();
        }

        @Override
        public Collection<Account> readAccountsWithBalanceOver(Double limit) {
            return null;
        }
    }

}
