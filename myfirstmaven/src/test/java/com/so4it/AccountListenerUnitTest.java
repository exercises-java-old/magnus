package com.so4it;

import com.so4it.domain.Account;
import com.so4it.messaging.AccountConsumer;
import com.so4it.messaging.AccountListener;
import com.so4it.messaging.AccountProducer;
import org.junit.Test;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class AccountListenerUnitTest {



    @Test
    public void testProducerConsumer() throws Exception{
        BlockingDeque<Account> accounts = new LinkedBlockingDeque<>();

        AccountProducer accountProducer = new AccountProducer(accounts);


        //Lambda expression
        //AccountConsumer accountConsumer = new AccountConsumer(accounts, System.out::println);


        //Anonymous class
        AccountListener accountListener = new AccountListener(){
            @Override
            public void onAccount(Account account) {
                System.out.println(account);
            }
        };
        //AccountConsumer accountConsumer = new AccountConsumer(accounts, accountListener);


        //Another way of doing lambda
        AccountConsumer accountConsumer = new AccountConsumer(accounts, account -> System.out.println(account));




        new Thread(accountProducer).start();
        new Thread(accountConsumer).start();

        Thread.sleep(5000);
    }
}
