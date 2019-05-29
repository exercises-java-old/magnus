package com.so4it.messaging;

import com.so4it.dao.AccountDao;
import com.so4it.domain.Account;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class AccountConsumer implements Runnable {

    private BlockingQueue<Account> accounts;

    private AccountDao accountDao;

    public AccountConsumer(BlockingQueue<Account> accounts,
                           AccountDao accountDao) {
        this.accounts = Objects.requireNonNull(accounts,"Accounts cannot be null");
        this.accountDao = Objects.requireNonNull(accountDao,"Account DAO cannot be null");
    }

    @Override
    public void run() {
        while(!Thread.interrupted()){
            try {
                Account account = accounts.poll(100L, TimeUnit.MILLISECONDS);
                if(account != null){
                    //@TODO Do something async on the account
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
