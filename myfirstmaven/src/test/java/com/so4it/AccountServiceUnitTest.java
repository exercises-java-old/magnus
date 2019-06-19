package com.so4it;

import com.so4it.dao.AccountDao;
import com.so4it.dao.AccountDaoImpl;
import com.so4it.domain.Account;
import com.so4it.messaging.AccountConsumer;
import com.so4it.messaging.AccountListener;
import com.so4it.messaging.AccountProducer;
import com.so4it.service.AccountService;
import com.so4it.service.AccountServiceImpl;
import com.so4it.service.AnotherAccountServiceImpl;
import com.so4it.util.Poller;
import com.so4it.util.SatisfiedWhenTrueReturned;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class AccountServiceUnitTest {

    @Test
    public void testGetBalance() throws Exception{
        AccountDao accountDao = new AccountDaoImpl();
        //accountDao.create(Account.builder().withId(1L).withBalance(100d).build());

        AtomicInteger atomicInteger = new AtomicInteger(0);

        List<AccountListener> accountListeners = new ArrayList<>();
        accountListeners.add(account -> atomicInteger.incrementAndGet());

        BlockingDeque<Account> queue = new LinkedBlockingDeque<>();
        AccountProducer accountProducer = new AccountProducer(queue);
        try(AccountConsumer accountConsumer = new AccountConsumer(queue,accountListeners).init()){
            AccountService accountService = new AccountServiceImpl(accountDao,accountProducer);
            accountService.create(Account.builder().withBalance(12.0).withId(1L).build());
            Poller.pollAndCheck(SatisfiedWhenTrueReturned.create( () -> atomicInteger.get() == 1));
        }
    }

}
