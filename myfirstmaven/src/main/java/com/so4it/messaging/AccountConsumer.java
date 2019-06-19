package com.so4it.messaging;

import com.so4it.dao.AccountDao;
import com.so4it.domain.Account;
import com.so4it.util.NamedThreadFactory;
import com.so4it.util.RecurringBlockingTask;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

public class AccountConsumer implements Runnable, AutoCloseable {


    private RunningTask runningTask;

    private BlockingQueue<Account> queue;


    private List<AccountListener> accountListeners = new CopyOnWriteArrayList<>();

    public AccountConsumer(BlockingQueue<Account> queue,
                           List<AccountListener> accountListeners) {
        this.queue = Objects.requireNonNull(queue,"Accounts cannot be null");
        this.accountListeners.addAll(Objects.requireNonNull(accountListeners,"Account DAO cannot be null"));
    }


    public AccountConsumer init(){
        runningTask = new RunningTask(queue,accountListeners);
        runningTask.start();
        return this;
    }


    @Override
    public void close() throws Exception {
        runningTask.close();
    }

    @Override
    public void run() {
        while(!Thread.interrupted()){
            try {
                Account account = queue.poll(100L, TimeUnit.MILLISECONDS);
                if(account != null){
                    accountListeners.forEach(al -> al.onAccount(account));
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }


    public static class RunningTask extends RecurringBlockingTask<Account> {


        private final List<AccountListener> accountListeners;



        public RunningTask(BlockingQueue<Account> queue,List<AccountListener> accountListeners) {
            super(new NamedThreadFactory("consumer-thread-"), queue);
            this.accountListeners = Objects.requireNonNull(accountListeners,"accountListeners");
        }

        @Override
        protected void doTask(Account account) {
            accountListeners.forEach(listener -> listener.onAccount(account));
        }

        @Override
        protected long getBackoff() {
            return 100L;
        }
    }
}
