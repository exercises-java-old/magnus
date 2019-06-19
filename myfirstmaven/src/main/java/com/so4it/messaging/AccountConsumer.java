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
import java.util.logging.Logger;

public class AccountConsumer implements AutoCloseable {


    private static final Logger LOGGER = Logger.getLogger("consumer");


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
        protected void onError(Throwable throwable, Account account) {
            LOGGER.severe(throwable.getMessage());
        }

        @Override
        protected long getBackoff() {
            return 100L;
        }
    }
}
