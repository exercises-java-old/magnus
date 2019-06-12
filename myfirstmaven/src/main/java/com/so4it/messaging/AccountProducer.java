package com.so4it.messaging;

import com.so4it.domain.Account;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class AccountProducer implements Runnable{


    private static final Random RANDOM = new Random();


    private BlockingDeque<Account> accounts;


    public AccountProducer(BlockingDeque<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()){
            try {
                accounts.add(Account.builder().withId(RANDOM.nextLong()).withBalance(RANDOM.nextDouble()).build());
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }
}
