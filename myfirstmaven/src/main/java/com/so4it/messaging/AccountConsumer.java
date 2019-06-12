package com.so4it.messaging;

import com.so4it.domain.Account;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

public class AccountConsumer implements Runnable {

    private BlockingQueue<Account> accounts;


    private List<AccountListener> accountListeners = new CopyOnWriteArrayList<>();

    public AccountConsumer(BlockingQueue<Account> accounts,
                           AccountListener accountListener) {
        this.accounts = Objects.requireNonNull(accounts,"Accounts cannot be null");
        accountListeners.add(Objects.requireNonNull(accountListener,"Account DAO cannot be null"));
    }

    @Override
    public void run() {
        while(!Thread.interrupted()){
            try {
                Account account = accounts.poll(100L, TimeUnit.MILLISECONDS);
                if(account != null){
                    accountListeners.forEach(al -> al.onAccount(account));
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
