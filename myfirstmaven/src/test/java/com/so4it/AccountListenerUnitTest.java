package com.so4it;

import com.so4it.domain.Account;
import com.so4it.messaging.AccountConsumer;
import com.so4it.messaging.AccountProducer;
import org.junit.Test;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class AccountListenerUnitTest {



    @Test
    public void testProducerConsumer() throws Exception{
        BlockingDeque<Account> accounts = new LinkedBlockingDeque<>();

        AccountProducer accountProducer = new AccountProducer(accounts);
        AccountConsumer accountConsumer = new AccountConsumer(accounts, System.out::println);

        new Thread(accountProducer).start();
        new Thread(accountConsumer).start();

        Thread.sleep(5000);
    }
}
