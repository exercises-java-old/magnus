package com.so4it.messaging;

import com.so4it.domain.Account;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

public class AccountProducer {

    private Queue<Account> accounts = new LinkedBlockingDeque<>();

    public boolean offer(Account account) {
        return accounts.offer(account);
    }

}
