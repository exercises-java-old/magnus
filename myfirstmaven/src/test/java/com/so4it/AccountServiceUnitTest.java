package com.so4it;

import com.so4it.dao.AccountDao;
import com.so4it.dao.AccountDaoImpl;
import com.so4it.domain.Account;
import com.so4it.service.AccountService;
import com.so4it.service.AccountServiceImpl;
import com.so4it.service.AnotherAccountServiceImpl;
import org.junit.Assert;
import org.junit.Test;

public class AccountServiceUnitTest {

    @Test
    public void testGetBalance() {
        AccountDao accountDao = new AccountDaoImpl();
        accountDao.create(Account.builder().withId(1L).withBalance(100d).build());
        AccountService accountService1 = new AccountServiceImpl(accountDao);
        //AccountService accountService2 = new AnotherAccountServiceImpl();
        Assert.assertEquals(Double.valueOf(100), accountService1.getBalance(1L));
        //Assert.assertNotEquals(43d, accountService2.getBalance(1L));
    }

}
